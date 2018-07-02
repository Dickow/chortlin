package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.interfaces.IMapAPI
import com.dickow.chortlin.core.api.interfaces.IProcessorAPI
import com.dickow.chortlin.core.message.IMessage

class MapAPI<TFunc, TEvent : IMessage> : IMapAPI<TFunc, TEvent> {
    override fun mapWith(func: TFunc): IProcessorAPI<TEvent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}