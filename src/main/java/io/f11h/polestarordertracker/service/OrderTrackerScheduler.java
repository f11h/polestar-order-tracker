package io.f11h.polestarordertracker.service;

import io.f11h.polestarordertracker.persistence.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderTrackerScheduler {

    private final OrderTrackerService orderTrackerService;

    @Scheduled(initialDelay = 10, fixedDelayString = "${polestar-order-tracker.back-off}", timeUnit = TimeUnit.SECONDS)
    public void scheduler() {
        Order nextOrder = orderTrackerService.getNextOrderToBeProcessed();

        if (nextOrder != null) {
            orderTrackerService.processOrder(nextOrder);
        }
    }
}
