package io.f11h.polestarordertracker.client;

import feign.Response;
import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "polestarIam", url = "${polestar-order-tracker.iam-url}")
public interface PolestarIamClient {

    @GetMapping("/as/authorization.oauth2")
    Response startLogin(
            @RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("scope") String scope);

    @PostMapping(value = "/as/{resumeToken}/resume/as/authorization.ping", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Response login(
            @PathVariable("resumeToken") String resumeToken,
            @RequestParam("client_id") String clientId,
            @RequestBody LoginData loginData,
            @RequestHeader("Cookie") String cookie);


    @AllArgsConstructor
    @Getter
    class LoginData {
        @FormProperty("pf.username")
        private String username;

        @FormProperty("pf.pass")
        private String password;
    }

}
