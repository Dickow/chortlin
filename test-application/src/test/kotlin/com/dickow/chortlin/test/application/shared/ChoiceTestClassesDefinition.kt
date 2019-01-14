package com.dickow.chortlin.test.application.shared

import com.dickow.chortlin.interception.annotations.Named
import com.dickow.chortlin.interception.annotations.TraceInvocation
import com.dickow.chortlin.interception.annotations.TraceReturn
import java.util.*

class AuthSession(val id: UUID)

class Auth {
    private val google = GoogleAuth()
    private val facebook = FacebookAuth()

    @TraceInvocation
    @TraceReturn
    fun authenticate(@Named("username") username: String, @Named("password") password: String): AuthSession {
        return if (username.endsWith("@gmail.com")) {
            when (google.login(username, password)) {
                true -> AuthSession(UUID.randomUUID())
                else -> throw RuntimeException("Error occurred when google authenticating")
            }
        } else {
            when (facebook.authenticate(username, password)) {
                true -> AuthSession(UUID.randomUUID())
                else -> throw RuntimeException("Error occurred when facebook authenticating")
            }
        }
    }
}

class GoogleAuth {
    @TraceInvocation
    @TraceReturn
    fun login(@Named("identifier") identifier: String, @Named("password") password: String): Boolean {
        return true
    }
}

class FacebookAuth {
    @TraceInvocation
    @TraceReturn
    fun authenticate(@Named("email") email: String, @Named("password") password: String): Boolean {
        return true
    }
}