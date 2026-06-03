package com.muma.spotify

import mu.KLogging
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.RetryListener

class SpotifyRetryListener : RetryListener {

    override fun <T, E : Throwable> onError(
        context: RetryContext,
        callback: RetryCallback<T, E>,
        throwable: Throwable,
    ) {
        logger.warn { "Spotify API 호출 실패 (${context.retryCount}번째 시도): ${throwable.javaClass.simpleName} - ${throwable.message}" }
    }

    override fun <T, E : Throwable> close(
        context: RetryContext,
        callback: RetryCallback<T, E>,
        throwable: Throwable?,
    ) {
        if (throwable != null) {
            logger.error { "Spotify API 재시도 횟수 초과 (총 ${context.retryCount}회 실패): ${throwable.javaClass.simpleName}" }
        }
    }

    companion object : KLogging()
}
