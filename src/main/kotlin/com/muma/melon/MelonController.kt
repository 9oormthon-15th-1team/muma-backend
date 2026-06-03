package com.muma.melon

import com.muma.common.response.ApiMeta
import com.muma.common.response.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/melon")
class MelonController(
    private val melonService: MelonService,
) {

    @PostMapping("/preview")
    fun preview(@RequestBody tracks: List<MelonTrackRequest>): ApiResponse<List<MelonTrackResult>> {
        val results = melonService.preview(tracks)
        return ApiResponse(data = results, meta = ApiMeta(code = "SUCCESS", message = null))
    }
}
