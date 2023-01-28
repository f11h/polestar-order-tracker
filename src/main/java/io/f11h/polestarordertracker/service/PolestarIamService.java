package io.f11h.polestarordertracker.service;

import feign.Response;
import io.f11h.polestarordertracker.client.AccessTokenResponse;
import io.f11h.polestarordertracker.client.GraphQueries;
import io.f11h.polestarordertracker.client.PolestarGraphClient;
import io.f11h.polestarordertracker.client.PolestarIamClient;
import io.f11h.polestarordertracker.persistence.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
@Slf4j
public class PolestarIamService {

    private final static String CALLBACK_URL = "https://www.polestar.com/sign-in-callback";
    private final static String OAUTH_CLIENT_ID = "polmystar";
    private final static String OAUTH_RESPONSE_TYPE = "code";
    private final static String OAUTH_SCOPES = "openid profile email customer:attributes customer:attributes:write";

    private final PolestarIamClient polestarIamClient;

    private final PolestarGraphClient polestarGraphClient;

    public String getAccessToken(Order order) {
        log.info("Start Login for {}", order.getOrderId());
        // Create Login Session and get Resume-Token for actual login
        Response startLoginResponse = polestarIamClient.startLogin(OAUTH_RESPONSE_TYPE, OAUTH_CLIENT_ID, CALLBACK_URL, OAUTH_SCOPES);
        startLoginResponse.close();

        if (!startLoginResponse.headers().containsKey(HttpHeaders.LOCATION)) {
            log.error("Login for {} failed: StartLoginResponse does not contain Location-Header.", order.getOrderId());
            return null;
        }
        if (!startLoginResponse.headers().containsKey(HttpHeaders.SET_COOKIE)) {
            log.error("Login for {} failed: StartLoginResponse does not contain Set-Cookie-Header.", order.getOrderId());
            return null;
        }

        String forwardUri = startLoginResponse.headers().get(HttpHeaders.LOCATION).iterator().next();
        String cookie = startLoginResponse.headers().get(HttpHeaders.SET_COOKIE).iterator().next();
        String resumeToken = getResumeTokenFromUri(forwardUri);
        if (resumeToken == null) {
            log.error("Login for {} failed: StartLoginResponse Forward URI does not contain Resume Token.", order.getOrderId());
            return null;
        }

        // Login with credentials and get AuthCode
        Response loginResponse = polestarIamClient.login(resumeToken, OAUTH_CLIENT_ID,
                new PolestarIamClient.LoginData(order.getLoginUsername(), order.getLoginPassword()), cookie);
        loginResponse.close();

        if (!loginResponse.headers().containsKey(HttpHeaders.LOCATION)) {
            log.error("Login for {} failed: LoginResponse does not contain Location-Header.", order.getOrderId());
            return null;
        }

        String loginCallbackUri = loginResponse.headers().get(HttpHeaders.LOCATION).iterator().next();
        String authCode = getAuthCodeFromUri(loginCallbackUri);

        if (authCode == null) {
            log.error("Login for {} failed: LoginResponse Forward URI does not contain AuthCode.", order.getOrderId());
            return null;
        }

        // Get AccessToken for further API requests
        ResponseEntity<AccessTokenResponse> accessTokenResponseResponse = polestarGraphClient.getAccessToken(
                GraphQueries.GET_ACCESS_TOKEN_QUERY,
                GraphQueries.GET_ACCESS_TOKEN_OPERATION,
                "{\"code\": \"" + authCode + "\"}");

        if (accessTokenResponseResponse.getBody() == null) {
            log.error("Login for {} failed: AccessTokenResponse does not have a body.", order.getOrderId());
            return null;
        }

        log.info("Login for {} successful", order.getOrderId());
        return accessTokenResponseResponse.getBody().getData().getGetAuthToken().getAccessToken();
    }

    private String getResumeTokenFromUri(String uri) {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(URI.create(uri)).build();
        return uriComponents.getQueryParams().get("resumePath").get(0);
    }

    private String getAuthCodeFromUri(String uri) {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(URI.create(uri)).build();
        return uriComponents.getQueryParams().get("code").get(0);
    }
}
