package com.muma.melon

import com.muma.spotify.SpotifySearchAdapter
import com.muma.spotify.SpotifySearchStrategy
import com.muma.spotify.dto.SpotifyTrack
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class MelonService(
    private val spotifySearchAdapter: SpotifySearchAdapter,
) {

    private val searchStrategies: List<SpotifySearchStrategy> = listOf(
        SpotifySearchStrategy { title, artists -> spotifySearchAdapter.searchTracks(title, artists) },
        SpotifySearchStrategy { title, _ -> spotifySearchAdapter.searchTracks(title, "") },
    )

    fun preview(tracks: List<MelonTrackRequest>): List<MelonTrackResult> {
        return tracks.map { track ->
            val title = track.title.take(100)
            val artists = if (track.artistsText.length >= 100) {
                track.artistsText.split(",").take(2).joinToString(",")
            } else {
                track.artistsText
            }
            MelonTrackResult(
                playlistId = track.playlistId,
                position = track.position,
                melonSongId = track.melonSongId,
                title = title,
                artistsText = artists,
                melonArtistIds = track.melonArtistIds,
                albumTitle = track.albumTitle,
                melonAlbumId = track.melonAlbumId,
                melonLikes = track.melonLikes,
                melonSongUrl = track.melonSongUrl,
                results = searchWithFallback(title, artists),
            )
        }
    }

    private fun searchWithFallback(title: String, artists: String): List<SpotifyTrack> {
        for (strategy in searchStrategies) {
            val results = strategy.search(title, artists)
            if (results.isNotEmpty()) return results
        }
        return emptyList()
    }

    companion object : KLogging()
}
