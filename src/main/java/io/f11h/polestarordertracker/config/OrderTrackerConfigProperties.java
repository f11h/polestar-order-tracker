package io.f11h.polestarordertracker.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Data
@ConfigurationProperties(prefix = "polestar-order-tracker")
@Validated
@NoArgsConstructor
public class OrderTrackerConfigProperties {

    /**
     * URL of Polestar IAM Service-
     */
    @URL
    @NotNull
    private String iamUrl = "https://polestarid.eu.polestar.com";

    /**
     * URL of Polestar GRAPH API to get actual data.
     */
    @URL
    @NotNull
    private String graphUrl = "https://pc-api.polestar.com/eu-north-1";

    /**
     * Interval in seconds how often the order data should be polled.
     */
    @Min(300) // 5 Minutes
    @Max(604800) // 1 Week
    private Integer refreshInterval = 21_600;

    /**
     * Time in seconds two wait between requests.
     */
    @Min(60) // 1 Minute
    @Max(600) // 10 Minutes
    private Integer backOff = 60;

    /**
     * List of Polestar order configs.
     */
    @Size(max = 10)
    private List<@Valid OrderConfig> orderConfigs = new ArrayList<>();

    /**
     * Configuration of outbound mail server to notify about order changes.
     */
    @NotNull
    private MailConfig mailConfig;

    @Data
    @NoArgsConstructor
    public static class MailConfig {

        /**
         * Email Sender (displayed as FROM)
         */
        @NotNull
        @Length(min = 1, max = 100)
        private String from;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OrderConfig {

        /**
         * Enable or disable tracking of this order.
         */
        @NotNull
        private Boolean enabled = Boolean.TRUE;

        /**
         * Polestar IAM Username
         */
        @Length(min = 1, max = 50)
        @NotNull
        private String username;

        /**
         * Polestar IAM Password
         */
        @Length(min = 1, max = 50)
        @NotNull
        private String password;

        /**
         * Order ID of the order to be tracked (UUID)
         */
        @UUID
        @NotNull
        private String orderId;

        /**
         * List of Email recipient who get notified about order changes
         */
        @Size(min = 1, max = 5)
        @NotNull
        private List<@Email @Length(max = 100) @NotNull String> notifyEmail;
    }


}
