package com.mirogal.cocktail.core.common.extension

data class ApiException(
        /**
         * Called api method.
         */
        val method: String,
        /**
         * Unique Api error code.
         */
        val code: Int,
        /**
         * Api error message.
         */
        val details: String,
        /**
         * HTTP status code.
         */
        val httpCode: Int,
        /**
         * Cause of Exception.
         */
        override val cause: Throwable? = null
) : RuntimeException(
        "Api error: method=[$method], code=[$code], details=[$details]",
        cause
) {

    companion object {
        // api error codes
        const val UNKNOWN_ERROR = -1

        // local error codes
        const val JSON_PARSE = 1
        const val SERVER_NOT_RESPONDING = 2
        const val SOCKET_TIMEOUT = 3
        const val SERVER_ERROR = 4
    }

}