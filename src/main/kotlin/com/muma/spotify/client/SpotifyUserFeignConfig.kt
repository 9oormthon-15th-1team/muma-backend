package com.muma.spotify.client

import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpotifyUserFeignConfig {

    @Bean
    fun spotifyUserFeignLoggerLevel(): Logger.Level = Logger.Level.BASIC
}
