package com.pakybytes.demo.springboot.cassandra.core.models.status


object MsgStatus {

    val CONTINUE = 100
    val SWITCHING_PROTOCOLS = 101

    val OK = 200
    val CREATED = 201
    val ACCEPTED = 202
    val NON_AUTHORITATIVE_INFORMATION = 203
    val NO_CONTENT = 204
    val RESET_CONTENT = 205
    val PARTIAL_CONTENT = 206
    val MULTI_STATUS = 207

    val MULTIPLE_CHOICES = 300
    val MOVED_PERMANENTLY = 301
    val FOUND = 302
    val SEE_OTHER = 303
    val NOT_MODIFIED = 304
    val USE_PROXY = 305
    val TEMPORARY_REDIRECT = 307
    val PERMANENT_REDIRECT = 308

    val BAD_REQUEST = 400
    val UNAUTHORIZED = 401
    val PAYMENT_REQUIRED = 402
    val FORBIDDEN = 403
    val NOT_FOUND = 404
    val METHOD_NOT_ALLOWED = 405
    val NOT_ACCEPTABLE = 406
    val PROXY_AUTHENTICATION_REQUIRED = 407
    val REQUEST_TIMEOUT = 408
    val CONFLICT = 409
    val GONE = 410
    val LENGTH_REQUIRED = 411
    val PRECONDITION_FAILED = 412
    val REQUEST_ENTITY_TOO_LARGE = 413
    val REQUEST_URI_TOO_LONG = 414
    val UNSUPPORTED_MEDIA_TYPE = 415
    val REQUESTED_RANGE_NOT_SATISFIABLE = 416
    val EXPECTATION_FAILED = 417
    val IM_A_TEAPOT = 418
    val UNPROCESSABLE_ENTITY = 422
    val LOCKED = 423
    val FAILED_DEPENDENCY = 424
    val UPGRADE_REQUIRED = 426
    val TOO_MANY_REQUESTS = 429
    val REQUEST_HEADER_FIELDS_TOO_LARGE = 431
    @Deprecated("Use TOO_MANY_REQUESTS instead", ReplaceWith("TOO_MANY_REQUESTS"))
    val TOO_MANY_REQUEST = TOO_MANY_REQUESTS

    val INTERNAL_SERVER_ERROR = 500
    val NOT_IMPLEMENTED = 501
    val BAD_GATEWAY = 502
    val SERVICE_UNAVAILABLE = 503
    val GATEWAY_TIMEOUT = 504
    val HTTP_VERSION_NOT_SUPPORTED = 505
    val INSUFFICIENT_STORAGE = 507

    // 400 extended
    val BAD_REQUEST_STRUCTURE = 40001
    val BAD_REQUEST_CONSTRAINTS_VIOLATION = 40002
    val BAD_REQUEST_CONSTRAINTS_DUPLICATE = 40003

    // 500 extended
    val DAL_ERROR = 50001

    /** Get the main code, not extended */
    fun getHTMLErrorCode(code: Int): Int {
        return Integer.parseInt(Integer.toString(code).substring(0, 3))
    }

    fun isClientError(code: Int): Boolean {
        val mainCode = getHTMLErrorCode(code)
        if (mainCode in 400..499) return true
        return false
    }

    fun isPersistenceCode(code: Int): Boolean {
        if (code == DAL_ERROR) return true
        return false
    }

    /**
     * Return true if the error returned to the client
     * is not supposed to be the original error, for example,
     * sql code should not go to the client
     */
    fun isPrivateMsg(code: Int) =
            isPersistenceCode(code) ||
                    code == MsgStatus.BAD_REQUEST_CONSTRAINTS_DUPLICATE

    fun isExtendedCode(code: Int) = code > 9999

    /** Gets the description of the error code
     * */
    fun getDescr(code: Int): String =
            when (code) {
                100 -> "Continue"
                101 -> "Switching Protocols"

                200 -> "Ok"
                201 -> "Created"
                202 -> "Accepted"
                203 -> "Non Authoritative Information"
                204 -> "No Content"
                205 -> "Reset Content"
                206 -> "Partial Content"
                207 -> "Multi Status"

                300 -> "Multiple Choices"
                301 -> "Moved Permanently"
                302 -> "Found"
                303 -> "See Other"
                304 -> "Not Modified"
                305 -> "Use Proxy"
                307 -> "Temporary Redirect"
                308 -> "Permanent Redirect"

                400 -> "Bad Request"
                40001 -> "Bad Request Structure"
                40002 -> "Bad Request Constraints Violation"
                40003 -> "Bad Request Constraints Duplicate"
                401 -> "Unauthorized"
                402 -> "Payment Required"
                403 -> "Forbidden"
                404 -> "Not found"
                40401 -> "Not found mandatory Fields"
                40402 -> "Not found Client"
                40403 -> "Not found Entity"
                40404 -> "Not found Distributor"
                40405 -> "Not found Producer"
                40406 -> "Not found Zone"
                40407 -> "Not found Source"
                40408 -> "Not found Wine Type"
                40409 -> "Not found Grape"
                405 -> "Method Not Allowed"
                406 -> "Not Acceptable"
                407 -> "proxy Authentication Required"
                408 -> "Request Timeout"
                409 -> "Conflict"
                410 -> "Gone"
                411 -> "Length required"
                412 -> "Precondition Failed"
                413 -> "Request Entity Too Large"
                414 -> "Request URI too Long"
                415 -> "Unsupported Media Type"
                416 -> "Requested Range Not Satisfiable"
                417 -> "Expectation Failed"
                422 -> "Unprocessable Entity"
                423 -> "Locked"
                424 -> "Failed Dependency"
                426 -> "Upgrade Required"
                429 -> "Too Many Requests"

                500 -> "Internal Server Error"
                50001 -> "Persistence Error"
                501 -> "Not Implemented"
                502 -> "Bad Gateway"
                503 -> "Service Unavailable"
                504 -> "Gateway Timeout"
                505 -> "HTTP Version Not Supported"
                507 -> "Insufficient Storage"

                else -> "Unknown status"
            }
}