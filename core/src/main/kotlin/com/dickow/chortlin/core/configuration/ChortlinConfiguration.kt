package com.dickow.chortlin.core.configuration

interface ChortlinConfiguration {
    fun applyTo(args: Array<Any>)
    fun applyTo()
}