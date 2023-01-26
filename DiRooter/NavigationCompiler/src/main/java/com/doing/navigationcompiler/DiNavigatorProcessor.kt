package com.doing.navigationcompiler

import com.doing.navigatorannotation.Destination
import com.doing.navigatorannotation.DestinationJava
import com.google.auto.service.AutoService
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class DiNavigatorProcessor : AbstractProcessor(){

    override fun process(annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?): Boolean {

        println("${Constant.TAG} >>> kotlin process $annotations")
        val elementsSet = roundEnv?.getElementsAnnotatedWith(Destination::class.java)
        println("${Constant.TAG} >>> kotlin process $elementsSet")
        if (elementsSet != null && elementsSet.isNotEmpty()) {
            elementsSet.forEach { element ->
                val typeElement = element as TypeElement

                val className = typeElement.qualifiedName.toString()

                println("${Constant.TAG} >>> kotlin className: $className")
            }
        }
        println("${Constant.TAG} >>> kotlin DiNavigator end")
        println()
        return false
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(DestinationJava::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

}