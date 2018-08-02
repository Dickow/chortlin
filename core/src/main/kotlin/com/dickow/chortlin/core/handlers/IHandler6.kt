package com.dickow.chortlin.core.handlers

interface IHandler6<T1, T2, T3, T4, T5, T6, TMapped, R> {
    fun mapInput(arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, arg6: T6): TMapped
    fun process(input: TMapped): R
}