package com.doing.navigationcompiler

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.doing.navigatorannotation.Destination
import com.google.auto.service.AutoService
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.lang.management.ManagementFactory
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
        val options = processingEnv.options

        println("${Constant.TAG} >>> Runtime: ${ManagementFactory.getRuntimeMXBean().name}")
        println("${Constant.TAG} >>> This: ${this.hashCode()}")
        println("${Constant.TAG} >>> DiNavigatorAPTManager singleTon HashCode:" +
                " ${DiNavigatorAPTJavaManager.getInstance()}")
        println("${Constant.TAG} >>> Options : $options")

//        println()
//        Throwable().printStackTrace()
//        println()
    }

    override fun process(annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?): Boolean {

        val elementsSet = roundEnv?.getElementsAnnotatedWith(Destination::class.java)
//        println("${Constant.TAG} >>> kotlin$count HashCode:${this.hashCode()} process $elementsSet")

        if (elementsSet != null && elementsSet.size < 1) {
            return false
        }

        val map = fillElementMap(elementsSet)
        try {
            val resource = filer.createResource(StandardLocation.CLASS_OUTPUT,
                "", Constant.OUTPUT_FILE_NAME)
            val resourcePath = resource.toUri().path
            val name = "/build/"
//            println("${Constant.TAG} >>> kotlin$count Resource Path: $resourcePath")
//            println("${Constant.TAG} >>> kotlin$count File Separator: ${File.separator}build${File.separator}")

            val index = resourcePath.indexOf(name)

//            println("${Constant.TAG} >>> kotlin$count Build Index: $index")

            val modulePath = resourcePath.substring(0, resourcePath.indexOf(name))
            val moduleName = modulePath.substring(modulePath.lastIndexOf("/") + 1, modulePath.length)
            val projectPath = modulePath.substring(0, modulePath.lastIndexOf("/"))

//            println("${Constant.TAG} >>> kotlin$count Module Path: $modulePath")
//            println("${Constant.TAG} >>> kotlin$count Module Name: $moduleName")

            val assetPath = "${projectPath}/app/src/main/assets"
//            println("${Constant.TAG} >>> kotlin$count Assets Path: $assetPath")

            createJsonFile(assetPath, map)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        println()
        count++
        return false
    }

    private fun createJsonFile(
        assetPath: String,
        map: MutableMap<String, DestinationEntity>
    ) {
        val file = File(assetPath)
        if (!file.exists()) {
            file.mkdirs()
        }

        val outputFile = File(assetPath, Constant.OUTPUT_FILE_NAME)
        println("${Constant.TAG} >>> kotlin$count OutputFile Exists: ${outputFile.exists()}")
        if (outputFile.exists()) {
            outputFile.bufferedReader().use { reader ->
                val destinations = JSON.parseObject(reader.readText(),
                    object : TypeReference<HashMap<String, DestinationEntity>>() {})
                println("${Constant.TAG} >>> kotlin$count Content: $destinations")
                println("${Constant.TAG} >>> kotlin$count Map Size: ${map.size}")
                println("${Constant.TAG} >>> kotlin$count Destinations Size: ${destinations.size}")

                map.putAll(destinations)
                println("${Constant.TAG} >>> kotlin$count Map Size: ${map.size}")
                println()
            }
            outputFile.delete()
        }

        val json = JSON.toJSONString(map)
//        println("${Constant.TAG} >>> kotlin$count Output Path: ${outputFile.absolutePath}")

        outputFile.createNewFile()
        OutputStreamWriter(FileOutputStream(outputFile)).use { writer ->
            writer.write(json)
            writer.flush()
        }
    }

    private fun fillElementMap(elementsSet: MutableSet<out Element>?):
            MutableMap<String, DestinationEntity> {
        val map = mutableMapOf<String, DestinationEntity>()
        if (elementsSet != null && elementsSet.isNotEmpty()) {
            elementsSet.forEach { element ->
                val typeElement = element as TypeElement

                val className = typeElement.qualifiedName.toString()
                val destination = typeElement.getAnnotation(Destination::class.java)
                val pageUrl = destination.pageUrl
                val isStarter = destination.isStarter

                val pageType = getDestinationType(typeElement) ?: return@forEach
//                println("${Constant.TAG} >>> kotlin$count ClassName: $className PageUrl: $pageUrl")

                if (map.contains(pageUrl)) {
                    messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Different page don't use same pageUrl: $pageUrl"
                    )
                } else {
                    map[pageUrl] = DestinationEntity().apply {
                        this.id = className.hashCode()
                        this.className = className
                        this.pageUrl = pageUrl
                        this.isStarter = isStarter
                        this.pageType = pageType.type
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

    override fun getSupportedOptions(): MutableSet<String> {
        return mutableSetOf(Constant.OPTION_MODULE_NAME)
    }

}