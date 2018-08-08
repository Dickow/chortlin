package com.dickow.chortlin.core.configuration.map

class Mapper<TMapped> constructor(private val mapper: () -> TMapped) : IMapper {

    override fun map(args: Array<Any?>): Any? {
        return mapper.invoke()
    }
}