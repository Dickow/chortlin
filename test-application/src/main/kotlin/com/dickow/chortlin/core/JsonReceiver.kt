package com.dickow.chortlin.core

import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO
import com.google.gson.Gson

class JsonReceiver {
    private val gson = Gson()

    fun receiveReturn(json: String?){
        if(json != null){
            val traceDTO = gson.fromJson(json, ReturnDTO::class.java)
            val trace = traceDTO.toReturn()
        }
    }

    fun receiveInvocation(json: String?){
        if(json != null){
            val traceDTO = gson.fromJson(json, InvocationDTO::class.java)
            val trace = traceDTO.toInvocation()
        }
    }
}