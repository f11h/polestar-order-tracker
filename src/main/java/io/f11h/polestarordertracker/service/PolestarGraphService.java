package io.f11h.polestarordertracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.f11h.polestarordertracker.client.GetOrderModelRequest;
import io.f11h.polestarordertracker.client.GraphQueries;
import io.f11h.polestarordertracker.client.PolestarGraphClient;
import io.f11h.polestarordertracker.persistence.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PolestarGraphService {

    private final PolestarGraphClient polestarGraphClient;

    private final PolestarIamService polestarIamService;

    public JsonNode getOrderModel(Order order) {
        String accessToken = polestarIamService.getAccessToken(order);

        if (accessToken == null) {
            log.error("Failed to get AccessToken for {}", order.getOrderId());
            return null;
        }

        GetOrderModelRequest request = GetOrderModelRequest.builder()
                .operationName(GraphQueries.GET_ORDER_MODEL_OPERATION)
                .query(GraphQueries.GET_ORDER_MODEL_QUERY)
                .variables(new GetOrderModelRequest.Variables(order.getOrderId()))
                .build();

        ResponseEntity<JsonNode> orderModelResponse = polestarGraphClient.order2Delivery(request, "Bearer " + accessToken);

        if (orderModelResponse.getBody() == null) {
            log.error("GetOrderModel for {} failed: GraphResponse does not have a body.", order.getOrderId());
            return null;
        }

        log.info("GetOrderModel for {} successful", order.getOrderId());
        return orderModelResponse.getBody();
    }
}
