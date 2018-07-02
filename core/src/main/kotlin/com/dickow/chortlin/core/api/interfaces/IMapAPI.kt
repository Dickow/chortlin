package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.message.IMessage

interface IMapAPI<TFunc, TMappedMsg : IMessage> {
    fun mapWith(func: TFunc): IProcessorAPI<TMappedMsg>
}