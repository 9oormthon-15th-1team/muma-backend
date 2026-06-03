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
                title = if (track.title.length >= 100) {
                    track.title.take(100)
                } else {
                    track.title
                },
                artistsText = if (track.artistsText.length >= 100) {
                    track.artistsText.split(",").take(2).joinToString(",")
                } else {
                    track.artistsText
                },
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
        val titleText = if (title.length >= 100) {
            title.take(100)
        } else {
            title
        }
        val artistsText = if (artists.length >= 100) {
            artists.split(",").take(2).joinToString(",")
        } else {
            artists
        }
        val trackIds = spotifyClient.search(
            query = "track:$titleText artist:$artistsText",
            type = "track",
            limit = 2,
        ).tracks?.items?.take(2)?.map { it.id } ?: return emptyList()

        return trackIds.map { spotifyClient.getTrack(it) }
    }

    companion object : KLogging()
}
