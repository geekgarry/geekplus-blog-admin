package com.geekplus.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * author     : geekplus
 * date       : 9/23/25 7:15 PM
 * description: //TODO
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> {
                    configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024); // 16MB buffer（按需）
                })
                .build();

        return builder
                .exchangeStrategies(strategies)
                .baseUrl("https://www.geekplus.xyz") // 可不设
                .build();
    }
}
