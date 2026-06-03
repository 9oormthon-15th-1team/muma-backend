package com.muma.spotify.cache

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "spotify_search_cache")
class SpotifySearchCache(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val artists: String,

    @Column(nullable = false)
    val limitCount: Int,

    @Column(nullable = false, columnDefinition = "TEXT")
    val result: String,

    @Column(nullable = false)
    val cachedAt: LocalDateTime = LocalDateTime.now(),
)
