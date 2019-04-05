package com.pakybytes.demo.springboot.cassandra.core.services.status

import com.pakybytes.demo.springboot.cassandra.core.models.status.MsgStatus
import com.pakybytes.demo.springboot.cassandra.core.models.status.StatusResult
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.SQLException
import java.util.*


@ControllerAdvice
@RestController
class ExceptionTranslator(val service: StatusService) : ResponseEntityExceptionHandler() {

    private val log = LoggerFactory.getLogger(ExceptionTranslator::class.java)

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<StatusResult> {
        val code = getCode(ex)
        val status = service.build(
                code,
                getMsg(code, ex),
                Date(),
                request.getDescription(false).replace("uri=", "", true)
        )
        return buildResponseEntity(status, code)
    }


    private fun getCode(ex: Exception): Int {
        if (ex is DuplicateKeyException) return MsgStatus.BAD_REQUEST_CONSTRAINTS_DUPLICATE
        if (ex is SQLException) return MsgStatus.DAL_ERROR
        if (ex is DataAccessException) return MsgStatus.DAL_ERROR
        return MsgStatus.INTERNAL_SERVER_ERROR
    }

    /** Some messages should not pass to client side, like,
     * for ex, persistence errors
     */
    private fun getMsg(code: Int, ex: Exception): String? {
        logError(code, ex)
        if (MsgStatus.isPrivateMsg(code)) return MsgStatus.getDescr(code)
        return ex.message
    }


    private fun logError(code: Int, ex: Exception) {
        if (isWarning(code, ex)) {
            log.warn(ex.message)
        } else {
            log.error("Error", ex)
        }
    }


    private fun isWarning(code: Int, ex: Exception): Boolean {
        return when {
            ex is DuplicateKeyException -> true
            MsgStatus.isExtendedCode(code) -> true
            else -> false
        }
    }

    private fun buildResponseEntity(status: StatusResult, code: Int): ResponseEntity<StatusResult> {
        return ResponseEntity(status, HttpStatus.valueOf(service.getErrorCode(code)))
    }
}