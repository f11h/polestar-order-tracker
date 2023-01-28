package io.f11h.polestarordertracker.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public
class AccessTokenResponse {
    private Data data;

    @lombok.Data
    public static class Data {

        @JsonProperty("getAuthToken")
        private Data.AuthToken getAuthToken;

        @lombok.Data
        public static class AuthToken {
            @JsonProperty("id_token")
            private String idToken;
            @JsonProperty("access_token")
            private String accessToken;
            @JsonProperty("refresh_token")
            private String refreshToken;
            @JsonProperty("expires_in")
            private Integer expiresIn;
        }
    }
}
