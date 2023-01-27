package com.doing.navigationcompiler

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.doing.navigatorannotation.Destination
import com.google.auto.service.AutoService
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.tools.Diagnostic
import javax.tools.StandardLocation

@AutoService(Processor::class)
class DiNavigatorProcessor : AbstractProcessor(){

    private var count = 1
    private lateinit var messager: Messager
    private lateinit var filer: Filer

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        this.messager = processingEnv.messager
        this.filer = processingEnv.filer
        val sourceVersion = processingEnv.sourceVersion
        println("${Constant.TAG} >>> kotlin$count init SourceVersion $sourceVersion")
    }

    override fun process(annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?): Boolean {

        val elementsSet = roundEnv?.getElementsAnnotatedWith(Destination::class.java)
        println("${Constant.TAG} >>> kotlin$count HashCode:${this.hashCode()} process $elementsSet")

        if (elementsSet != null && elementsSet.size < 1) {
            return false
        }

        val map = fillElementMap(elementsSet)
        try {
            val resource = filer.createResource(StandardLocation.CLASS_OUTPUT,
                "", Constant.OUTPUT_FILE_NAME)
            val resourcePath = resource.toUri().path
            val name = "/build/"
            println("${Constant.TAG} >>> kotlin$count Resource Path: $resourcePath")
            println("${Constant.TAG} >>> kotlin$count File Separator: ${File.separator}build${File.separator}")

            val index = resourcePath.indexOf(name)

            println("${Constant.TAG} >>> kotlin$count Build Index: $index")

            val modulePath = resourcePath.substring(0, resourcePath.indexOf(name))
            val moduleName = modulePath.substring(modulePath.lastIndexOf("/"), modulePath.length)
            val assetPath = "${modulePath}/src/main/assets"

            println("${Constant.TAG} >>> kotlin$count Module Path: $modulePath")
            println("${Constant.TAG} >>> kotlin$count Assets Path: $assetPath")

            val file = File(assetPath)
            if (!file.exists()) {
                file.mkdirs()
            }

            val json = JSON.toJSONString(map)
            val outputFile = File(assetPath, Constant.OUTPUT_FILE_NAME)
            if (outputFile.exists()) {
                outputFile.delete()
            }

            println("${Constant.TAG} >>> kotlin$count Output Path: ${outputFile.absolutePath}")

            outputFile.createNewFile()
            OutputStreamWriter(FileOutputStream(outputFile, true)).use { writer ->
                writer.write(json)
                writer.flush()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("${Constant.TAG} >>> kotlin$count Resource Error ")
        }

        println()
        count++
        return false
    }

    private fun fillElementMap(elementsSet: MutableSet<out Element>?):
            Map<String, JSONObject> {
        val map = mutableMapOf<String, JSONObject>()
        if (elementsSet != null && elementsSet.isNotEmpty()) {
            elementsSet.forEach { element ->
                val typeElement = element as TypeElement

                val className = typeElement.qualifiedName.toString()
                val destination = typeElement.getAnnotation(Destination::class.java)
                val pageUrl = destination.pageUrl
                val isStarter = destination.isStarter

                val pageType = getDestinationType(typeElement) ?: return@forEach
                println("${Constant.TAG} >>> kotlin$count ClassName: $className PageUrl: $pageUrl")

                if (map.contains(pageUrl)) {
                    messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Different page don't use same pageUrl: $pageUrl"
                    )
                } else {
                    map[pageUrl] = JSONObject().apply {
                        put("className", className)
                        put("pageUrl", pageUrl)
                        put("isStarter", isStarter)
                        put("id", className.hashCode())
                        put("pageType", pageType.type)
                    }
                }
            }
        }
        return map
    }

    private fun getDestinationType(typeElement: TypeElement): Constant.PageType? {
        val superClass = typeElement.superclass

        val superClassName = superClass.toString().lowercase()
        if (superClassName.contains(Constant.TypeActivity.type.lowercase())) {
            return Constant.TypeActivity
        } else if (superClassName.contains(Constant.TypeFragment.type.lowercase())) {
            return Constant.TypeFragment
        } else if (superClassName.contains(Constant.TypeDialog.type.lowercase())) {
            return Constant.TypeDialog
        }

        if (superClass is DeclaredType) {
            val element = superClass.asElement()
            if (element is TypeElement) {
                return getDestinationType(element)
            }
        }

        return null
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Destination::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

}