package com.dickow.chortlin.core.handlers

interface IHandler4<T1, T2, T3, T4, TMapped, R> {
    fun mapInput(arg1: T1, arg2: T2, arg3: T3, arg4: T4): TMapped
    fun process(input: TMapped): R
}