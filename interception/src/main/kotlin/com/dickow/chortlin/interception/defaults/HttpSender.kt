package com.dickow.chortlin.interception.defaults

import com.dickow.chortlin.interception.configuration.HttpConfiguration
import com.dickow.chortlin.interception.sending.TraceSender
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class HttpSender(private val configuration: HttpConfiguration) : TraceSender {
    private val httpClient = HttpClient.newHttpClient()

    override fun send(invocationDTO: DtoDefinitions.InvocationDTO) {
        val request = HttpRequest.newBuilder(URI(configuration.invocationTraceEndpoint))
                .POST(java.net.http.HttpRequest.BodyPublishers.ofByteArray(invocationDTO.toByteArray()))
                .header("content-type", "application/octet-stream")
                .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() >= 300 || response.statusCode() <= 199) {
            throw ChoreographyRuntimeException("Error occurred in the choreography checker")
        }
    }

    override fun send(returnDTO: DtoDefinitions.ReturnDTO) {
        val request = HttpRequest.newBuilder(URI(configuration.returnTraceEndpoint))
                .POST(java.net.http.HttpRequest.BodyPublishers.ofByteArray(returnDTO.toByteArray()))
                .header("content-type", "application/octet-stream")
                .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() >= 300 || response.statusCode() <= 199) {
            throw ChoreographyRuntimeException("Error occurred in the choreography checker")
        }
    }
}