package io.f11h.polestarordertracker.persistence.entity;

import io.f11h.polestarordertracker.config.OrderTrackerConfigProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_config")
@RequiredArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    private String orderId;

    @Column
    private Boolean enabled;

    @Column(length = 100)
    private String loginUsername;

    @Column(length = 100)
    private String loginPassword;

    @Column(length = 1010)
    private String notifyEmails;

    @Column
    private LocalDateTime lastExecution;

    @Column(length = 50_000)
    private String orderData;

    public Order(OrderTrackerConfigProperties.OrderConfig orderConfigProperties) {
        orderId = orderConfigProperties.getOrderId();
        enabled = orderConfigProperties.getEnabled();
        loginUsername = orderConfigProperties.getUsername();
        loginPassword = orderConfigProperties.getPassword();
        notifyEmails = String.join(",", orderConfigProperties.getNotifyEmail());
    }
}
