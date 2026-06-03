package com.muma.spotify.cache

import org.springframework.data.jpa.repository.JpaRepository

interface SpotifySearchCacheRepository : JpaRepository<SpotifySearchCache, Long> {
    fun findByTitleAndArtistsAndLimitCount(title: String, artists: String, limitCount: Int): SpotifySearchCache?
}
