package com.apis

import com.fasterxml.jackson.databind.*
import io.ktor.serialization.gson.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
        }

        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    routing {
        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
