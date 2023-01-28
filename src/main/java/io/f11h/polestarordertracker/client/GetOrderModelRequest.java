package io.f11h.polestarordertracker.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public
class GetOrderModelRequest {
    private String operationName;
    private String query;
    private Variables variables;

    @Data
    @AllArgsConstructor
    public static class Variables {

        public Variables(String orderId) {
            request = new Request(orderId);
        }

        private Variables.Request request;

        @Data
        @AllArgsConstructor
        public static class Request {
            private String id;
        }
    }
}
