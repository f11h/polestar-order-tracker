package io.f11h.polestarordertracker.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "polestarGraph", url = "${polestar-order-tracker.graph-url}")
public interface PolestarGraphClient {

    @GetMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccessTokenResponse> getAccessToken(
            @RequestParam("query") String query,
            @RequestParam("operation_name") String operationName,
            @RequestParam("variables") String variables);

    @PostMapping(value = "/order2delivery", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JsonNode> order2Delivery(
            @RequestBody GetOrderModelRequest getOrderModelRequest,
            @RequestHeader("Authorization") String authorizationHeader);

}
