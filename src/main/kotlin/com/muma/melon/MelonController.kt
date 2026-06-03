package com.muma.melon

import com.muma.common.response.ApiMeta
import com.muma.common.response.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Melon", description = "멜론 곡 정보 관련 API")
@RestController
@RequestMapping("/api/melon")
class MelonController(
    private val melonService: MelonService,
) {

    @Operation(
        summary = "멜론 곡 Spotify 매핑 미리보기",
        description = "멜론 곡 목록을 입력받아 Spotify에서 매핑되는 트랙을 검색하고 상위 2개의 상세 정보를 함께 반환합니다.",
    )
    @PostMapping("/preview")
    fun preview(@RequestBody tracks: List<MelonTrackRequest>): ApiResponse<List<MelonTrackResult>> {
        val results = melonService.preview(tracks)
        return ApiResponse(data = results, meta = ApiMeta(code = "SUCCESS", message = null))
    }
}
