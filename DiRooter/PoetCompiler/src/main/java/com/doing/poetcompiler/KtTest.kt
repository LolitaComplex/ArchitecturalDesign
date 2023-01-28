package com.doing.poetcompiler

import java.io.File

class KtTest {
}

fun main() {
    val target = "com.doing.javapoet".replace(".", File.separator)
    println(target)
}