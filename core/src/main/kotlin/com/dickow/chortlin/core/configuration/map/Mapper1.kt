package com.dickow.chortlin.core.configuration.map

class Mapper1<T1, TMapped> constructor(private val mapper: (T1) -> TMapped) : IMapper {

    @Suppress("UNCHECKED_CAST")
    override fun map(args: Array<Any>): Any {
        return mapper.invoke(args[0] as T1) as Any
    }
}