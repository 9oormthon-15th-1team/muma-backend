package com.muma.csv

import com.muma.spotify.SpotifyTrackSearchService
import mu.KLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class TrackInfoEventListener(
    private val spotifyTrackSearchService: SpotifyTrackSearchService,
) {

    @Async
    @EventListener
    fun handle(event: TrackInfoReceivedEvent) {
        val tracks = spotifyTrackSearchService.searchTracks(
            title = event.title,
            artists = event.artists,
        )
        logger.info { "[${event.title} / ${event.artists}] Spotify 검색 결과 trackIds: ${tracks.map { it.id }}" }

        tracks.forEach { track ->
            logger.info { "track 상세: id=${track.id}, name=${track.name}, artists=${track.artists.map { it.name }}, album=${track.album.name}, durationMs=${track.durationMs}" }
        }
    }

    companion object : KLogging()
}
