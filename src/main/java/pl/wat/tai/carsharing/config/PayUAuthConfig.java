package pl.wat.tai.carsharing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pl.wat.tai.carsharing.payu.service.auth.PayUAuthToken;
import pl.wat.tai.carsharing.payu.service.auth.PayUClientCredentialsAuthenticator;

import java.util.Collections;

@RequiredArgsConstructor
@Configuration
class PayUAuthConfig {

    private final PayUClientCredentialsAuthenticator payUClientCredentialsAuthenticator;

    @Bean("payuApiRestTemplate")
    public RestTemplate payuRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setInterceptors(Collections.singletonList((httpRequest, bytes, clientHttpRequestExecution) -> {
            final PayUAuthToken payUAuthToken = payUClientCredentialsAuthenticator.authenticate();
            final HttpHeaders headers = httpRequest.getHeaders();
            headers.add("Authorization", payUAuthToken.getTokenType() + " " + payUAuthToken.getAccessToken());
            if (!headers.containsKey("Content-Type")) {
                headers.add("Content-Type", "application/json");
            }
            headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }));

        return restTemplate;
    }
}