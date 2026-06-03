package com.muma.spotify.client

import com.muma.spotify.dto.SpotifySearchResponse
import com.muma.spotify.dto.SpotifyTrack
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "spotify",
    url = "\${spotify.base-url}",
    configuration = [SpotifyFeignConfig::class],
)
interface SpotifyClient {

    @GetMapping("/search")
    fun search(
        @RequestParam(name = "q") query: String,
        @RequestParam type: String,
        @RequestParam(required = false) market: String? = "KR",
        @RequestParam(required = false) limit: Int? = null,
        @RequestParam(required = false) offset: Int? = null,
    ): SpotifySearchResponse

    @GetMapping("/tracks/{trackId}")
    fun getTrack(
        @PathVariable trackId: String,
        @RequestParam(required = false) market: String? = null,
    ): SpotifyTrack
}
