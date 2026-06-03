package com.muma.spotify

import feign.FeignException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate

@Configuration
class SpotifyRetryConfig {

    @Bean
    fun spotifyRetryTemplate(): RetryTemplate {
        val retryPolicy = SimpleRetryPolicy(
            4,
            mapOf(FeignException.TooManyRequests::class.java to true),
            true, // 예외가 래핑된 경우에도 원인 체인 탐색
        )
        return RetryTemplate().apply {
            setRetryPolicy(retryPolicy)
            setBackOffPolicy(RetryAfterAwareBackoffPolicy())
            registerListener(SpotifyRetryListener())
        }
    }
}
