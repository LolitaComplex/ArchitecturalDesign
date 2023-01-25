package com.doing.navigationcompiler

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement


class DiNavigatorProcessor : AbstractProcessor(){

    override fun process(annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?): Boolean {


        return false
    }

}