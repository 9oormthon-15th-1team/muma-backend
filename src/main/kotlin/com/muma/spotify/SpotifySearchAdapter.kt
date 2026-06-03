package com.muma.spotify

import com.muma.spotify.client.SpotifyClient
import com.muma.spotify.dto.SpotifyTrack
import mu.KLogging
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component

@Component
class SpotifySearchAdapter(
    private val spotifyClient: SpotifyClient,
    private val spotifyRetryTemplate: RetryTemplate,
) {

    fun searchTracks(title: String, artists: String, limit: Int = 2): List<SpotifyTrack> {
        val query = if (artists.isBlank()) "track:$title" else "track:$title artist:$artists"
        logger.debug("Spotify 검색: title=$title, artists=$artists")
        return spotifyRetryTemplate.execute<List<SpotifyTrack>, Throwable> { _ ->
            spotifyClient.search(
                query = query,
                type = "track",
                limit = limit,
            ).tracks?.items?.take(limit) ?: emptyList()
        }
    }

    companion object : KLogging()
}
