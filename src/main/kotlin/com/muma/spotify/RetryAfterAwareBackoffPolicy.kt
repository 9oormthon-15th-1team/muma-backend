package com.muma.spotify

import feign.FeignException
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
                Thread.sleep(retryAfterSeconds * 1000 + jitter)
                return
            }
        }

        fallback.backOff(ctx.fallbackContext)
    }

    private class Context(
        val retryContext: RetryContext,
        val fallbackContext: BackOffContext,
    ) : BackOffContext
}
