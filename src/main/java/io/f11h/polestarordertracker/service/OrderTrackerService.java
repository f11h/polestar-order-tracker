package io.f11h.polestarordertracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.DiffFlags;
import com.flipkart.zjsonpatch.JsonDiff;
import io.f11h.polestarordertracker.config.OrderTrackerConfigProperties;
import io.f11h.polestarordertracker.persistence.entity.Order;
import io.f11h.polestarordertracker.persistence.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.EnumSet;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderTrackerService {

    private final OrderTrackerConfigProperties properties;

    private final PolestarGraphService polestarGraphService;

    private final EmailService emailService;

    private final OrderRepository orderRepository;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void importOrders() {
        properties.getOrderConfigs().forEach(orderConfig -> {
            log.info("Importing Order with ID {} to database", orderConfig.getOrderId());
            Order orderEntity = new Order(orderConfig);
            orderRepository.save(orderEntity);
            log.info("Imported Order with ID {} to database", orderConfig.getOrderId());
        });

        log.info("Total amount of imported Orders {}", orderRepository.count());
    }

    public Order getNextOrderToBeProcessed() {
        LocalDateTime threshold = LocalDateTime.now()
                .minusSeconds(properties.getRefreshInterval());

        return orderRepository.getFirstByEnabledIsTrueAndLastExecutionIsNullOrLastExecutionIsBeforeOrderByLastExecutionAsc(threshold)
                .orElse(null);
    }

    public void processOrder(Order order) {
        // Get OrderModel from DB
        JsonNode storedOrderModel = null;
        if (order.getOrderData() != null) {
            try {
                storedOrderModel = objectMapper.readValue(order.getOrderData(), JsonNode.class);
            } catch (JsonProcessingException e) {
                log.error("Failed to parse JSON in database. Database entity will be truncated.", e);
                updateOrder(order, null);
                return;
            }
        }

        // Get OrderModel from Polestar API
        JsonNode currentOrderModel = polestarGraphService.getOrderModel(order);
        // Failed to retrieve Order Model
        if (currentOrderModel == null) {
            updateOrder(order, order.getOrderData());
            return;
        }

        // Only compare and notify user if DB already had an OrderModel
        if (storedOrderModel != null) {
            int storedOrderModelVersion = storedOrderModel.get("data").get("order").get("version").asInt();
            int currentOrderModelVersion = currentOrderModel.get("data").get("order").get("version").asInt();
            log.info("Order {}: StoredOrderModelVersion {}, CurrentOrderModelVersion {}", order.getOrderId(), storedOrderModelVersion, currentOrderModelVersion);

            EnumSet<DiffFlags> flags = EnumSet.of(
                    DiffFlags.ADD_ORIGINAL_VALUE_ON_REPLACE,
                    DiffFlags.OMIT_COPY_OPERATION,
                    DiffFlags.OMIT_MOVE_OPERATION
            );

            JsonNode difference = JsonDiff.asJson(storedOrderModel, currentOrderModel, flags);
            if (difference.isEmpty()) {
                log.info("Order {}: No Changes in OrderModel detected", order.getOrderId());
            } else {
                emailService.notifyUser(order, difference, storedOrderModelVersion, currentOrderModelVersion);
            }
        }

        // Update orderData in database
        String currentOrderModelAsString = order.getOrderData();
        try {
            currentOrderModelAsString = objectMapper.writeValueAsString(currentOrderModel);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert JsonNode to String value", e);
        }
        updateOrder(order, currentOrderModelAsString);
    }

    private void updateOrder(Order order, String orderData) {
        order.setLastExecution(LocalDateTime.now());
        order.setOrderData(orderData);
        orderRepository.save(order);
    }
}
