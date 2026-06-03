package com.muma.spotify.client

import com.fasterxml.jackson.annotation.JsonProperty
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.util.Base64

@Component
class SpotifyAuthInterceptor(
    @Value("\${spotify.client-id}") private val clientId: String,
    @Value("\${spotify.client-secret}") private val clientSecret: String,
) : RequestInterceptor {

    private val tokenClient = RestClient.create()

    @Volatile
    private var cachedToken: TokenEntry? = null

    override fun apply(template: RequestTemplate) {
        template.header(HttpHeaders.AUTHORIZATION, "Bearer ${getAccessToken()}")
    }

    private fun getAccessToken(): String {
        val current = cachedToken
        if (current != null && !current.isExpired()) return current.token

        return synchronized(this) {
            val checked = cachedToken
            if (checked != null && !checked.isExpired()) return checked.token

            val credentials = Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())
            val response = tokenClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic $credentials")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("grant_type=client_credentials")
                .retrieve()
                .body(SpotifyTokenResponse::class.java)!!

            cachedToken = TokenEntry(response.accessToken, System.currentTimeMillis() + response.expiresIn * 1000L - 10_000L)
            cachedToken!!.token
        }
    }

    data class SpotifyTokenResponse(
        @field:JsonProperty("access_token") val accessToken: String,
        @field:JsonProperty("expires_in") val expiresIn: Long,
    )

    private data class TokenEntry(val token: String, val expiresAt: Long) {
        fun isExpired() = System.currentTimeMillis() >= expiresAt
    }
}
