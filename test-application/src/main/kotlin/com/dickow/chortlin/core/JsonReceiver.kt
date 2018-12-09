package com.dickow.chortlin.core

import com.dickow.chortlin.shared.trace.dto.TraceElementDTO
import com.google.gson.Gson

class JsonReceiver {
    private val gson = Gson()
    fun receive(json: String?) {
        if(json != null){
            val trace = gson.fromJson(json, TraceElementDTO::class.java)
            System.out.println(trace.toString())
        }
    }
}