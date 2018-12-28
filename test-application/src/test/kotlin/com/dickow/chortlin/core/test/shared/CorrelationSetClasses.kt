@file:Suppress("UNUSED_PARAMETER")

package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.core.test.shared.objects.Receipt
import com.dickow.chortlin.interception.annotations.TraceInvocation
import com.dickow.chortlin.interception.annotations.TraceReturn
import java.util.*

data class AuthResult(val userId: String)

class Authentication {
    @TraceInvocation
    @TraceReturn
    fun authenticate(username: String, password: String): AuthResult {
        return AuthResult(UUID.randomUUID().toString())
    }
}

class AuthenticatedService {
    @TraceInvocation
    @TraceReturn
    fun buyItem(item: String, authResult: AuthResult): Boolean {
        return true
    }

    @TraceInvocation
    @TraceReturn
    fun sellItem(item: String, price: Int, authResult: AuthResult): Receipt {
        return Receipt(100, price, item, authResult.userId)
    }
}
