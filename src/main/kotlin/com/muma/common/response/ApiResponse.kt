package com.muma.common.response

data class ApiResponse<T>(
    val data: T?,
    val meta: ApiMeta,
) {
    companion object {
        fun success() = ApiResponse<Unit>(
            data = null,
            meta = ApiMeta(code = "SUCCESS", message = null),
        )
    }
}

data class ApiMeta(
    val code: String,
    val message: String?,
)
