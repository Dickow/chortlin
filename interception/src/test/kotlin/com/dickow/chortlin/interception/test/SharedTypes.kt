package com.dickow.chortlin.interception.test

import com.dickow.chortlin.interception.annotations.*

class Login{
    @TraceInvocation
    @TraceReturn
    fun authenticate(username: String, password: String) : Boolean{
        return false
    }
}

class MerchantService{

    @TraceInvocation
    @TraceReturn
    fun buyItem(itemId: Int, buyerId: String) : String{
        return "Success"
    }

    @TraceInvocation
    @TraceReturn
    fun sellItem(itemId: Int, price: Int){}
}

class AnonymousService{
    fun unknownMethod(input: String){}
}