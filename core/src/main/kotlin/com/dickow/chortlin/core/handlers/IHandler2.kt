package com.dickow.chortlin.core.handlers

interface IHandler2<T1, T2, TMapped, R> {
    fun mapInput(arg1: T1, arg2: T2): TMapped
    fun process(input: TMapped): R
}