package com.dickow.chortlin.core.handlers

interface IHandler<TMapped, R> {
    fun mapInput(): TMapped
    fun process(input: TMapped): R
}