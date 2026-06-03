package com.muma.spotify.client

import com.muma.spotify.dto.SpotifyAddPlaylistItemsRequest
import com.muma.spotify.dto.SpotifyCreatePlaylistRequest
import com.muma.spotify.dto.SpotifyPaging
import com.muma.spotify.dto.SpotifyPlaylist
import com.muma.spotify.dto.SpotifyPlaylistItem
import com.muma.spotify.dto.SpotifySnapshotResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "spotify-user",
    url = "\${spotify.base-url}",
    configuration = [SpotifyUserFeignConfig::class],
)
interface SpotifyUserClient {

    @GetMapping("/me/playlists")
    fun getMyPlaylists(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam(required = false) limit: Int? = null,
        @RequestParam(required = false) offset: Int? = null,
    ): SpotifyPaging<SpotifyPlaylist>

    @GetMapping("/playlists/{playlistId}/items")
    fun getPlaylistItems(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable playlistId: String,
        @RequestParam(required = false) limit: Int? = null,
        @RequestParam(required = false) offset: Int? = null,
        @RequestParam(required = false) market: String? = null,
    ): SpotifyPaging<SpotifyPlaylistItem>

    @PostMapping("/me/playlists")
    fun createPlaylist(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestBody request: SpotifyCreatePlaylistRequest,
    ): SpotifyPlaylist

    @PostMapping("/playlists/{playlistId}/items")
    fun addPlaylistItems(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable playlistId: String,
        @RequestBody request: SpotifyAddPlaylistItemsRequest,
    ): SpotifySnapshotResponse
}
