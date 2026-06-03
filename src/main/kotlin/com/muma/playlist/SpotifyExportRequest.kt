package com.muma.playlist

import com.fasterxml.jackson.annotation.JsonProperty

data class SpotifyExportRequest(
    val name: String,
    @field:JsonProperty("track_ids") val trackIds: List<String>,
)
