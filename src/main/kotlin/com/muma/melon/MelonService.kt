package com.muma.melon

import com.muma.spotify.SpotifySearchAdapter
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class MelonService(
    private val spotifySearchAdapter: SpotifySearchAdapter,
) {

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
                results = spotifySearchAdapter.searchTracks(title, artists),
            )
        }
    }

    companion object : KLogging()
}
