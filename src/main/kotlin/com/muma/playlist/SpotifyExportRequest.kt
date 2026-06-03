package com.muma.playlist

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Spotify 플레이리스트 생성 요청")
data class SpotifyExportRequest(
    @Schema(description = "생성할 플레이리스트 이름", example = "My Muma Playlist")
    @field:JsonProperty("playlist_name") val playlistName: String,

    @Schema(description = "추가할 Spotify 트랙 ID 목록", example = "[\"4iV5W9uYEdYUVa79Axb7Rh\", \"1301WleyT98MSxVHPZCA6M\"]")
    @field:JsonProperty("track_ids") val trackIds: List<String>,
)
