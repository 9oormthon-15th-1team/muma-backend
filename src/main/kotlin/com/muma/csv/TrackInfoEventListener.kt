package com.muma.csv

import com.muma.spotify.SpotifyTrackSearchService
import com.muma.spotify.client.SpotifyClient
import mu.KLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class TrackInfoEventListener(
    private val spotifyTrackSearchService: SpotifyTrackSearchService,
    private val spotifyClient: SpotifyClient,
) {

    @Async
    @EventListener
    fun handle(event: TrackInfoReceivedEvent) {
        val trackIds = spotifyTrackSearchService.searchTrackIds(
            title = event.title,
            artists = event.artists,
        )
        logger.info { "[${event.title} / ${event.artists}] Spotify 검색 결과 trackIds: $trackIds" }

        trackIds.forEach { trackId ->
            val track = spotifyClient.getTrack(trackId)
            logger.info { "track 상세: id=${track.id}, name=${track.name}, artists=${track.artists.map { it.name }}, album=${track.album.name}, durationMs=${track.durationMs}" }
        }
    }

    companion object : KLogging()
}
