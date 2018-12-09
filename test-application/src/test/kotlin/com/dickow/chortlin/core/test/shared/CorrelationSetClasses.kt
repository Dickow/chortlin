@file:Suppress("UNUSED_PARAMETER")

package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke
import com.dickow.chortlin.shared.annotations.ChortlinOnReturn
import java.util.*

class AuthResult(val userId: String)

class Authentication {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun authenticate(username: String, password: String): AuthResult {
        return AuthResult(UUID.randomUUID().toString())
    }
}

class AuthenticatedService {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun buyItem(item: String, authResult: AuthResult): Boolean {
        return true
    }

    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun sellItem(item: String, price: Int, authResult: AuthResult): Boolean {
        return false
    }
}
