package com.muma.playlist

import com.muma.common.response.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Spotify Export", description = "Spotify 플레이리스트 내보내기 API")
@RestController
@RequestMapping("/api/spotify/export")
class SpotifyExportController(
    private val spotifyExportService: SpotifyExportService,
) {

    @Operation(
        summary = "Spotify 플레이리스트 생성 및 트랙 추가",
        description = "플레이리스트를 생성하고 전달받은 트랙 ID 목록을 추가합니다. Spotify 사용자 OAuth 토큰이 필요합니다.",
    )
    @PostMapping
    fun create(
        @Parameter(description = "Spotify 사용자 OAuth2 Access Token", required = true, example = "BQA...")
        @RequestHeader("X-Muma-Spotify-Token") spotifyToken: String,
        @RequestBody request: SpotifyExportRequest,
    ): ApiResponse<Unit> {
        spotifyExportService.createAndAddTracks(request, spotifyToken)
        return ApiResponse.success()
    }
}
