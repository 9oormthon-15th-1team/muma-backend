package com.muma.spotify

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.muma.spotify.cache.SpotifySearchCache
import com.muma.spotify.cache.SpotifySearchCacheRepository
import com.muma.spotify.client.SpotifyClient
import com.muma.spotify.dto.SpotifyTrack
import mu.KLogging
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component

@Component
class SpotifySearchAdapter(
    private val spotifyClient: SpotifyClient,
    private val spotifyRetryTemplate: RetryTemplate,
    private val cacheRepository: SpotifySearchCacheRepository,
    private val objectMapper: ObjectMapper,
) {

    fun searchTracks(title: String, artists: String, limit: Int = 2): List<SpotifyTrack> {
        val cached = cacheRepository.findByTitleAndArtistsAndLimitCount(title, artists, limit)
        if (cached != null) {
            logger.debug("캐시 히트: title=$title, artists=$artists")
            return objectMapper.readValue(cached.result)
        }

        val query = if (artists.isBlank()) "track:$title" else "track:$title artist:$artists"
        logger.debug("Spotify 검색: title=$title, artists=$artists")
        val tracks = spotifyRetryTemplate.execute<List<SpotifyTrack>, Throwable> { _ ->
            spotifyClient.search(
                query = query,
                type = "track",
                limit = limit,
            ).tracks?.items?.take(limit) ?: emptyList()
        }

        cacheRepository.save(
            SpotifySearchCache(
                title = title,
                artists = artists,
                limitCount = limit,
                result = objectMapper.writeValueAsString(tracks),
            )
        )

        return tracks
    }

    companion object : KLogging()
}
