package com.doing.archtitectural

fun main() {
    val animal = Panda()
    animal.eat()
}

interface Animal {
    fun eat()
}

class Panda : Animal {
    override fun eat() {
        println("吃")
    }
}

fun Panda.eat() {
    this.eat()
    println("吃竹子")
}

fun Panda.carrot(decorator: () -> Unit) {
    println("吃红萝卜")
    decorator()
}