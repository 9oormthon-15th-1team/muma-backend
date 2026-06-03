package com.muma.spotify

import com.muma.spotify.dto.SpotifyAlbumSimple
import com.muma.spotify.dto.SpotifyArtistSimple
import com.muma.spotify.dto.SpotifyExternalUrls
import com.muma.spotify.dto.SpotifyTrack
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SpotifyTrackSearchServiceTest {

    private val spotifySearchAdapter: SpotifySearchAdapter = mock()
    private val service = SpotifyTrackSearchService(spotifySearchAdapter)

    @Test
    fun `검색 결과에서 track 목록을 반환한다`() {
        val tracks = listOf(
            track(id = "id-1", name = "Dynamite"),
            track(id = "id-2", name = "Dynamite (Acoustic)"),
        )
        given(spotifySearchAdapter.searchTracks(any(), any(), any()))
            .willReturn(tracks)

        val result = service.searchTracks(title = "Dynamite", artists = "BTS")

        assertThat(result).isEqualTo(tracks)
        verify(spotifySearchAdapter).searchTracks(title = "Dynamite", artists = "BTS")
    }

    @Test
    fun `검색 결과가 없으면 빈 목록을 반환한다`() {
        given(spotifySearchAdapter.searchTracks(any(), any(), any()))
            .willReturn(emptyList())

        val result = service.searchTracks(title = "없는곡", artists = "없는아티스트")

        assertThat(result).isEmpty()
    }

    private fun track(id: String, name: String) = SpotifyTrack(
        id = id,
        name = name,
        durationMs = 200000,
        explicit = false,
        popularity = 80,
        artists = listOf(SpotifyArtistSimple(id = "artist-1", name = "BTS", externalUrls = SpotifyExternalUrls("https://open.spotify.com"))),
        album = SpotifyAlbumSimple(id = "album-1", name = "BE", albumType = "album", releaseDate = "2020-11-20", images = emptyList()),
        externalUrls = SpotifyExternalUrls("https://open.spotify.com"),
        previewUrl = null,
    )
}
