package com.muma.spotify

import com.muma.spotify.client.SpotifyClient
import com.muma.spotify.dto.SpotifyTrack
import feign.FeignException
import mu.KLogging
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component

@Component
class SpotifySearchAdapter(
    private val spotifyClient: SpotifyClient,
) {

    @Retryable(
        retryFor = [FeignException.TooManyRequests::class],
        maxAttempts = 4,
        backoff = Backoff(delay = 500, multiplier = 2.0, maxDelay = 4000),
    )
    fun searchTracks(title: String, artists: String, limit: Int = 2): List<SpotifyTrack> {
        logger.debug("Spotify 검색: title=$title, artists=$artists")
        return spotifyClient.search(
            query = "track:$title artist:$artists",
            type = "track",
            limit = limit,
        ).tracks?.items?.take(limit) ?: emptyList()
    }

    companion object : KLogging()
}
