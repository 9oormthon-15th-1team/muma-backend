package com.muma.spotify.client

import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpotifyFeignConfig {

    @Bean
    fun feignLoggerLevel(): Logger.Level = Logger.Level.BASIC
}
