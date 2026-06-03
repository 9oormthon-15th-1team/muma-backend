package com.muma.playlist

import com.fasterxml.jackson.annotation.JsonProperty

data class SpotifyExportRequest(
    @field:JsonProperty("playlist_name") val playlistName: String,
    @field:JsonProperty("track_ids") val trackIds: List<String>,
)
