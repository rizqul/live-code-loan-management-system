package com.enigmacamp.challlcloanmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${api.credit-scoring.base-url:https://leptosomic-unransomed-anisha.ngrok-free.dev}")
    private String creditScoringUrl;

    @Value("${groq.api.url:https://api.groq.com/openai/v1/chat/completions}")
    private String groqBaseUrl;

    private ClientHttpRequestInterceptor loggingInterceptor(){
        return(((request, body, execution) -> {
            System.out.println("Request : " + request.getMethod() + " " + request.getURI());
            System.out.println("Headers : " + request.getHeaders());
            System.out.println("Body : " + new String(body));
            return execution.execute(request,body);
        }));
    }

    private SimpleClientHttpRequestFactory timeOutConfiguration() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);

        return factory;
    }

    private SimpleClientHttpRequestFactory groqTimeoutConfiguration() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(60000);
        return factory;
    }

    @Bean
    @Primary
    public RestClient restClient(){
        return RestClient.builder()
            .baseUrl(creditScoringUrl)
            .build();
    }

    @Bean("creditScoringClient")
    public RestClient creditScoringClient(){
        return RestClient.builder()
                .baseUrl(creditScoringUrl)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .defaultHeader("X-Custom-Header", "RestClient Demo")
                .requestInterceptor(loggingInterceptor())
                .requestFactory(timeOutConfiguration())
                .build();
    }

    @Bean("groqClient")
    public RestClient groqClient() {
        return RestClient.builder()
                .baseUrl(groqBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .requestInterceptor(loggingInterceptor())
                .requestFactory(groqTimeoutConfiguration())
                .build();
    }
}
