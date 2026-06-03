package com.muma.playlist

import com.muma.spotify.client.SpotifyUserClient
import com.muma.spotify.dto.SpotifyAddPlaylistItemsRequest
import com.muma.spotify.dto.SpotifyCreatePlaylistRequest
import org.springframework.stereotype.Service

@Service
class SpotifyExportService(
    private val spotifyUserClient: SpotifyUserClient,
) {

    fun createAndAddTracks(request: SpotifyExportRequest, spotifyToken: String) {
        val bearerToken = "Bearer $spotifyToken"

        val playlist = spotifyUserClient.createPlaylist(
            bearerToken = bearerToken,
            request = SpotifyCreatePlaylistRequest(name = request.name),
        )

        if (request.trackIds.isNotEmpty()) {
            spotifyUserClient.addPlaylistItems(
                bearerToken = bearerToken,
                playlistId = playlist.id,
                request = SpotifyAddPlaylistItemsRequest(
                    uris = request.trackIds.map { "spotify:track:$it" },
                ),
            )
        }
    }
}
