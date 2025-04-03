package com.point.kutils.extensions

fun <T> Result<T>.ignoreValue() = this.map { }