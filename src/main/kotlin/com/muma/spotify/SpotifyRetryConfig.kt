package com.muma.spotify

import feign.FeignException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.support.RetryTemplate

@Configuration
class SpotifyRetryConfig {

    @Bean
    fun spotifyRetryTemplate(): RetryTemplate =
        RetryTemplate.builder()
            .maxAttempts(4)
            .retryOn(FeignException.TooManyRequests::class.java)
            .customBackoff(RetryAfterAwareBackoffPolicy())
            .build()
}
