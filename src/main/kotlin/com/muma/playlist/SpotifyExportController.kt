package com.muma.playlist

import com.muma.common.response.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/spotify/export")
class SpotifyExportController(
    private val spotifyExportService: SpotifyExportService,
) {

    @PostMapping
    fun create(
        @RequestHeader("X-Muma-Spotify-Token") spotifyToken: String,
        @RequestBody request: SpotifyExportRequest,
    ): ApiResponse<Unit> {
        spotifyExportService.createAndAddTracks(request, spotifyToken)
        return ApiResponse.success()
    }
}
