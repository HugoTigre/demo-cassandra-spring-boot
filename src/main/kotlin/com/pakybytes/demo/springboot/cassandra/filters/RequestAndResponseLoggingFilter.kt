package com.pakybytes.demo.springboot.cassandra.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.pakybytes.demo.springboot.cassandra.conf.SpringBootCassandraDemoConfig
import com.pakybytes.demo.springboot.cassandra.core.models.log.HttpBody
import com.pakybytes.demo.springboot.cassandra.core.models.log.HttpHeaderRequest
import com.pakybytes.demo.springboot.cassandra.core.models.log.HttpHeaderResponse
import com.pakybytes.demo.springboot.cassandra.core.services.utils.JsonUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Spring Web filter for logging request and response.
 *
 * @see org.springframework.web.filter.AbstractRequestLoggingFilter
 * @see ContentCachingRequestWrapper
 * @see ContentCachingResponseWrapper
 */
@Component
class RequestAndResponseLoggingFilter(
        val conf: SpringBootCassandraDemoConfig,
        val jsonUtils: JsonUtils) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(RequestAndResponseLoggingFilter::class.java)
    private val requestPrefix = "|>"
    private val responsePrefix = "|<"

    @Autowired
    private lateinit var jsonMapper: ObjectMapper

    private val VISIBLE_TYPES = arrayListOf(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
    )

    @Throws(ServletException::class, IOException::class)
    override
    fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response)
        } else {
            doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain)
        }
    }

    @Throws(ServletException::class, IOException::class)
    protected fun doFilterWrapped(request: ContentCachingRequestWrapper, response: ContentCachingResponseWrapper, filterChain: FilterChain) {
        try {
            beforeRequest(request, response)
            filterChain.doFilter(request, response)
        } finally {
            afterRequest(request, response)
            response.copyBodyToResponse()
        }
    }

    protected fun beforeRequest(request: ContentCachingRequestWrapper, response: ContentCachingResponseWrapper) {
        if (log.isInfoEnabled() && conf.logRequests) {
            logRequestHeader(request, requestPrefix)
        }
    }

    protected fun afterRequest(request: ContentCachingRequestWrapper, response: ContentCachingResponseWrapper) {
        if (log.isInfoEnabled()) {
            if (conf.logRequests) logRequestBody(request, requestPrefix)
            if (conf.logResponses) logResponse(response, responsePrefix)
        }
    }

    private fun logRequestHeader(request: ContentCachingRequestWrapper, prefix: String) {
        val header = HttpHeaderRequest(
                request.remoteAddr,
                request.method,
                request.requestURI,
                request.queryString,
                Collections.list(request.headerNames).map {
                    it to Collections.list(request.getHeaders(it)).map {
                        it
                    }
                }.toMap()
        )

        log.info("$prefix HEADER: ${jsonUtils.toJson(header)}")
    }

    private fun logRequestBody(request: ContentCachingRequestWrapper, prefix: String) {
        val content = request.contentAsByteArray
        if (content.size > 0) {
            logContent(content, request.contentType, request.characterEncoding, prefix)
        }
    }

    private fun logResponse(response: ContentCachingResponseWrapper, prefix: String) {
        val status = response.status
        val resp = HttpHeaderResponse(
                status,
                HttpStatus.valueOf(status).getReasonPhrase(),
                response.headerNames.map {
                    it to response.getHeaders(it).map { it }
                }.toMap()
        )
        log.info("$prefix HEADER: ${jsonUtils.toJson(resp)}")
        val content = response.contentAsByteArray
        if (content.size > 0) {
            logContent(content, response.contentType, response.characterEncoding, prefix)
        }
    }

    private fun logContent(content: ByteArray, contentType: String, contentEncoding: String, prefix: String) {
        val mediaType = MediaType.valueOf(contentType)
        val visible = VISIBLE_TYPES.stream().anyMatch({ visibleType -> visibleType.includes(mediaType) })
        if (visible) {
            try {
                val contentString = String(content, Charset.forName(contentEncoding))
                val httpBody = HttpBody(
                        contentString.split("\r\n|\r|\n").map { line -> formatContentLine(line) },
//                        contentString,
                        content.size
                )
                log.info("$prefix BODY ${jsonUtils.toJson(httpBody)}")
            } catch (e: UnsupportedEncodingException) {
                log.warn("{} [{} bytes content]", prefix, content.size)
            }
        } else {
            log.info("{} [{} bytes content]", prefix, content.size)
        }
    }

    private fun wrapRequest(request: HttpServletRequest): ContentCachingRequestWrapper {
        return request as? ContentCachingRequestWrapper ?: ContentCachingRequestWrapper(request)
    }

    private fun wrapResponse(response: HttpServletResponse): ContentCachingResponseWrapper {
        return response as? ContentCachingResponseWrapper ?: ContentCachingResponseWrapper(response)
    }

    private fun formatContentLine(line: String): String {
        return line.replace("\n", "", true)
                .replace("\t", "", true)
    }

}