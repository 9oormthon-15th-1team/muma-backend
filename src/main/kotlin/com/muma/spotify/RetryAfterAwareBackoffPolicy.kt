package com.muma.spotify

import feign.FeignException
import mu.KLogging
import org.springframework.retry.RetryContext
import org.springframework.retry.backoff.BackOffContext
import org.springframework.retry.backoff.BackOffPolicy
import org.springframework.retry.backoff.ExponentialBackOffPolicy
import kotlin.random.Random

class RetryAfterAwareBackoffPolicy(
    private val jitterMaxMs: Long = 500,
) : BackOffPolicy {

    private val fallback = ExponentialBackOffPolicy().apply {
        initialInterval = 1000
        multiplier = 2.0
        maxInterval = 8000
    }

    override fun start(context: RetryContext): BackOffContext {
        return Context(context, fallback.start(context))
    }

    override fun backOff(backOffContext: BackOffContext) {
        val ctx = backOffContext as Context
        val ex = ctx.retryContext.lastThrowable

        if (ex is FeignException.TooManyRequests) {
            val retryAfterSeconds = ex.responseHeaders()
                .entries.firstOrNull { it.key.equals("Retry-After", ignoreCase = true) }
                ?.value?.firstOrNull()?.toLongOrNull()

            if (retryAfterSeconds != null) {
                val jitter = Random.nextLong(jitterMaxMs)
                val sleepMs = retryAfterSeconds * 1000 + jitter
                logger.warn { "Spotify 429 - Retry-After: ${retryAfterSeconds}s, jitter: ${jitter}ms, 대기 후 재시도 (${sleepMs}ms)" }
                Thread.sleep(sleepMs)
                return
            }
        }

        fallback.backOff(ctx.fallbackContext)
    }

    companion object : KLogging()

    private class Context(
        val retryContext: RetryContext,
        val fallbackContext: BackOffContext,
    ) : BackOffContext
}
