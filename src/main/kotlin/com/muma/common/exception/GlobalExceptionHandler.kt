package com.muma.common.exception

import com.muma.common.response.ApiMeta
import com.muma.common.response.ApiResponse
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        logger.error("Unhandled exception", e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse(
                    data = null,
                    meta = ApiMeta(code = "FAILURE", message = "일시적인 오류가 발생했습니다."),
                )
            )
    }

    companion object : KLogging()
}
