package com.pakybytes.demo.springboot.cassandra.core.services.status

import com.pakybytes.demo.springboot.cassandra.conf.SpringBootCassandraDemoConfig
import com.pakybytes.demo.springboot.cassandra.core.models.status.MsgStatus
import com.pakybytes.demo.springboot.cassandra.core.models.status.StatusResult
import org.springframework.stereotype.Service
import java.util.*


@Service
class StatusService(val conf: SpringBootCassandraDemoConfig) {

    fun getAppVersion() = conf.app.version

    fun getAppId() = conf.app.name + ":" + conf.app.version + ":" + conf.env

    fun build(id: Int,
              status: String? = null,
              timestamp: Date? = null,
              path: String? = null,
              token: String? = null,
              resourceId: String? = null): StatusResult {

        return StatusResult(
                id,
                if (status != null) status else MsgStatus.getDescr(id),
                timestamp,
                path,
                getAppVersion(),
                getAppId(),
                token,
                resourceId)
    }

    /** Get the main code, not extended  */
    fun getErrorCode(code: Int): Int =
            Integer.parseInt(Integer.toString(code).substring(0, 3))
}