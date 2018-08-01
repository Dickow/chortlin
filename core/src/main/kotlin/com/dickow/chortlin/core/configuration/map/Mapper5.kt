package com.dickow.chortlin.core.configuration.map

class Mapper5<T1, T2, T3, T4, T5, TMapped> constructor(private val mapper: (T1, T2, T3, T4, T5) -> TMapped) : IMapper {

    @Suppress("UNCHECKED_CAST")
    override fun map(args: Array<Any>): Any {
        return mapper.invoke(args[0] as T1, args[1] as T2, args[2] as T3, args[3] as T4, args[4] as T5) as Any
    }
}