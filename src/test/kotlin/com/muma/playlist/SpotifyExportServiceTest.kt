package com.muma.playlist

import com.muma.spotify.client.SpotifyUserClient
import com.muma.spotify.dto.SpotifyAddPlaylistItemsRequest
import com.muma.spotify.dto.SpotifyCreatePlaylistRequest
import com.muma.spotify.dto.SpotifyExternalUrls
import com.muma.spotify.dto.SpotifyImage
import com.muma.spotify.dto.SpotifyPlaylist
import com.muma.spotify.dto.SpotifyPlaylistTracksRef
import com.muma.spotify.dto.SpotifySnapshotResponse
import com.muma.spotify.dto.SpotifyUser
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

class SpotifyExportServiceTest {

    private val spotifyUserClient: SpotifyUserClient = mock()
    private val service = SpotifyExportService(spotifyUserClient)

    @Test
    fun `플레이리스트 생성 후 트랙을 추가한다`() {
        val playlist = playlist(id = "playlist-id")
        given(spotifyUserClient.createPlaylist(any(), any())).willReturn(playlist)
        given(spotifyUserClient.addPlaylistItems(any(), any(), any())).willReturn(SpotifySnapshotResponse("snapshot-1"))

        service.createAndAddTracks(
            request = SpotifyExportRequest(playlistName = "Test Playlist", trackIds = listOf("track-1", "track-2")),
            spotifyToken = "test-token",
        )

        verify(spotifyUserClient).createPlaylist(
            bearerToken = "Bearer test-token",
            request = SpotifyCreatePlaylistRequest(name = "Test Playlist"),
        )
        verify(spotifyUserClient).addPlaylistItems(
            bearerToken = "Bearer test-token",
            playlistId = "playlist-id",
            request = SpotifyAddPlaylistItemsRequest(uris = listOf("spotify:track:track-1", "spotify:track:track-2")),
        )
    }

    @Test
    fun `트랙 목록이 비어있으면 트랙 추가를 호출하지 않는다`() {
        given(spotifyUserClient.createPlaylist(any(), any())).willReturn(playlist(id = "playlist-id"))

        service.createAndAddTracks(
            request = SpotifyExportRequest(playlistName = "Empty Playlist", trackIds = emptyList()),
            spotifyToken = "test-token",
        )

        verify(spotifyUserClient, never()).addPlaylistItems(any(), any(), any())
    }

    private fun playlist(id: String) = SpotifyPlaylist(
        id = id,
        name = "Test Playlist",
        description = null,
        public = false,
        owner = SpotifyUser(id = "user-1", displayName = "Test User"),
        images = emptyList<SpotifyImage>(),
        tracks = SpotifyPlaylistTracksRef(href = "", total = 0),
        externalUrls = SpotifyExternalUrls("https://open.spotify.com"),
    )
}
