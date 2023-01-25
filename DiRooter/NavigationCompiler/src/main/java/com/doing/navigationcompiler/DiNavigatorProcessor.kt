package com.doing.navigationcompiler

import com.doing.navigatorannotation.Destination
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement


class DiNavigatorProcessor : AbstractProcessor(){

    override fun process(annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?): Boolean {

        val elementsSet = roundEnv?.getElementsAnnotatedWith(Destination::class.java)
        if (elementsSet != null && elementsSet.isNotEmpty()) {
            elementsSet.forEach { element ->
                val typeElement = element as TypeElement

                val className = typeElement.qualifiedName.toString()

                println("DiNavigatorProcessor processor className: $className")
            }
        }
        return false
    }

}