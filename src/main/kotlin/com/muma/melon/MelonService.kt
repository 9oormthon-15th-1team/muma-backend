package com.muma.melon

import com.muma.spotify.client.SpotifyClient
import com.muma.spotify.dto.SpotifyTrack
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class MelonService(
    private val spotifyClient: SpotifyClient,
) {

    fun preview(tracks: List<MelonTrackRequest>): List<MelonTrackResult> {
        return tracks.map { track ->
            val spotifyTracks = searchTopTracks(track.title, track.artistsText)
            MelonTrackResult(
                playlistId = track.playlistId,
                position = track.position,
                melonSongId = track.melonSongId,
                title = track.title,
                artistsText = track.artistsText,
                melonArtistIds = track.melonArtistIds,
                albumTitle = track.albumTitle,
                melonAlbumId = track.melonAlbumId,
                melonLikes = track.melonLikes,
                melonSongUrl = track.melonSongUrl,
                results = spotifyTracks,
            )
        }
    }

    private fun searchTopTracks(title: String, artists: String): List<SpotifyTrack> {
        val trackIds = spotifyClient.search(
            query = "track:$title artist:$artists",
            type = "track",
            limit = 2,
        ).tracks?.items?.take(2)?.map { it.id } ?: return emptyList()

        return trackIds.map { spotifyClient.getTrack(it) }
    }

    companion object : KLogging()
}
