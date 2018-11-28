package com.dickow.chortlin.core.test.shared

class AuthResult(val userId: String)

class Authentication {
    fun authenticate(username: String, password: String): AuthResult {
        return AuthResult(username)
    }
}

class AuthenticatedService {
    fun buyItem(item: String, authResult: AuthResult): Boolean {
        return true
    }

    fun sellItem(item: String, price: Int, authResult: AuthResult): Boolean {
        return false
    }
}
