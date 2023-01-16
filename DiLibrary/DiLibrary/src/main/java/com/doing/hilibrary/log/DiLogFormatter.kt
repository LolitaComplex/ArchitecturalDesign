package com.doing.hilibrary.log

interface DiLogFormatter<T> {

    fun format(t: T): String


}