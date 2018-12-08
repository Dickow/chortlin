package com.dickow.chortlin.interception.test

import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke
import com.dickow.chortlin.shared.annotations.ChortlinOnReturn

class Login{
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun authenticate(username: String, password: String) : Boolean{
        return false
    }
}

class MerchantService{

    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun buyItem(itemId: Int, buyerId: String) : String{
        return "Success"
    }

    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun sellItem(itemId: Int, price: Int){}
}

class AnonymousService{
    fun unknownMethod(input: String){}
}