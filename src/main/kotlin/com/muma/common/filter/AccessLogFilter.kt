package com.muma.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
class AccessLogFilter : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val uri = request.requestURI
        return EXCLUDED_PREFIXES.any { uri.startsWith(it) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val wrappedRequest = ContentCachingRequestWrapper(request)
        val wrappedResponse = ContentCachingResponseWrapper(response)

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse)
        } finally {
            log(wrappedRequest, wrappedResponse)
            wrappedResponse.copyBodyToResponse()
        }
    }

    private fun log(request: ContentCachingRequestWrapper, response: ContentCachingResponseWrapper) {
        val requestBody = request.contentAsByteArray.toString(Charsets.UTF_8).trim()
        val responseBody = response.contentAsByteArray.toString(Charsets.UTF_8).trim()
        val uri = request.requestURI + (request.queryString?.let { "?$it" } ?: "")
        val customHeaders = request.headerNames.toList()
            .filter { it.startsWith("x-", ignoreCase = true) }
            .joinToString(", ") { "$it=${request.getHeader(it)}" }

        val message = """
            |
            |>>> ${request.method} $uri
            |    Headers  : ${customHeaders.ifEmpty { "(none)" }}
            |    Request  : ${requestBody.ifEmpty { "(empty)" }}
            |    Status   : ${response.status}
            |    Response : ${responseBody.ifEmpty { "(empty)" }}
        """.trimMargin()

        logger.info(message)
    }

    companion object : KLogging() {
        private val EXCLUDED_PREFIXES = listOf(
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars",
        )
    }
}
