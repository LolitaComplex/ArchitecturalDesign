package com.doing.poetcompiler

import com.doing.navigatorannotation.Destination
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import java.io.FileOutputStream
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.StandardLocation

@AutoService(Processor::class)
class PoetProcessor : AbstractProcessor() {

    companion object {
        var count = 0
    }

    private lateinit var TAG :String
    private lateinit var mElementUtil: Elements
    private lateinit var mTypeUtils: Types
    private lateinit var mFiler: Filer
    private var mModuleName = ""
    private var isApp = false
    private var mPoetName: String? = null

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)

        val moduleName = processingEnv.options[PoetCompilerConstant.OPTION_MODULE_NAME]
        this.TAG = "$moduleName >>> ${PoetCompilerConstant.TAG}"

        this.mElementUtil = processingEnv.elementUtils
        this.mTypeUtils = processingEnv.typeUtils
        this.mFiler = processingEnv.filer
        this.mModuleName = moduleName ?: ""
        this.isApp = processingEnv.options[PoetCompilerConstant.OPTION_IS_APP] == "true"
        this.mPoetName = processingEnv.options[PoetCompilerConstant.OPTION_POET_NAME]
        println("$TAG init SourceVersion: ${processingEnv.sourceVersion}" +
                " evn hashcode: ${processingEnv.hashCode()} count: ${count++}")
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val filer = mFiler
        val moduleName = mModuleName
        val targetModule = mPoetName
        val elements = roundEnv?.getElementsAnnotatedWith(Destination::class.java)

        if (elements == null || elements.size < 1) {
            return false
        }

        val pageList = mutableListOf<String>()
        elements.forEach { element ->
            val typeElement = element as TypeElement
            val className = typeElement.qualifiedName
            pageList.add(className.toString())
        }

        val outputFile = getTempFile(filer, moduleName)
        if (moduleName.isNotEmpty() && isApp && targetModule != null) {
            makeJavaFile(filer, moduleName, targetModule)
            outputFile.bufferedReader().useLines { lines ->
                lines.filter { it.isNotEmpty() }.forEach { line ->
                    pageList.add(line)
                }
            }
            if (outputFile.exists()) {
                outputFile.delete()
            }
            makeKotlinFile(filer, pageList)
        } else if (!isApp) {
            FileOutputStream(outputFile, true).bufferedWriter().use { writer->
                pageList.forEach { line ->
                    writer.write("${line}\n")
                }
                writer.flush()
            }
        }


        return false
    }

    private fun makeKotlinFile(filer: Filer, pageList: MutableList<String>) {
        println("$TAG process: kotlin List size: ${pageList.size}")

//        val packageName = "com.doing.poet.kotlin"
//        val fileName = "PageModel"
//        val pageModelClass = ClassName(packageName, fileName)
//        val pageModelFile = FileSpec.builder(packageName, fileName)
//            .addType(
//                TypeSpec.classBuilder(fileName)
//                    .addProperty(
//                        PropertySpec.builder("pageList", typeNameOf<MutableList<String>>())
//                            .initializer(buildCodeBlock {
//                                mutableListOf<String>()
//                            })
//                            .build()
//                    ).addInitializerBlock(buildCodeBlock {
//
//                    })
//                    .build()
//            )

    }

    private fun getTempFile(filer: Filer, moduleName: String): File {
        val resource = filer.createResource(StandardLocation.SOURCE_OUTPUT,
            "", PoetCompilerConstant.TEMP_DATA_FILE
        )
        val name = resource.name
        val path = resource.toUri().path

        val moduleIndex = name.indexOf("${File.separator}$moduleName${File.separator}")
        val rootDir = name.substring(0, moduleIndex)
        println("$TAG process: resource name: $name \t path: $path \t " +
            "moduleIndex: $moduleIndex \t rootDir: $rootDir")
        return File(rootDir, PoetCompilerConstant.TEMP_DATA_FILE)
    }

    private fun makeJavaFile(filer: Filer, moduleName: String, targetModule: String) {
        val packageName = "com.doing.poet.java"

        val javaFile = CodeUtil.javaFile(packageName, TAG)
        val targetFile = getTargetFile(filer, packageName, javaFile.typeSpec.name, moduleName, targetModule)
        println("$TAG makeHelloJavaPoet name: ${targetFile.absolutePath} typeSpec.name: ${javaFile.typeSpec.name}")
        javaFile.writeTo(targetFile)
    }



    private fun getTargetFile(filer: Filer, packageName: String, fileName: String,
        currentModuleName: String, targetModule: String): File {

        val resource = filer.createResource(
            StandardLocation.SOURCE_OUTPUT,
            "", fileName
        )
        val targetResourcePath = resource.name.toString().replace(
            "${File.separator}$currentModuleName${File.separator}",
            "${File.separator}$targetModule${File.separator}"
        )

        val resourceDir = targetResourcePath.substring(0,
            targetResourcePath.lastIndexOf(File.separator) + 1)
        val fileDir = File("${resourceDir}${packageName.replace(".", File.separator)}")
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }

        return File(fileDir, fileName)
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Destination::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedOptions(): MutableSet<String> {
        return mutableSetOf(
            PoetCompilerConstant.OPTION_MODULE_NAME,
            PoetCompilerConstant.OPTION_IS_APP,
            PoetCompilerConstant.OPTION_POET_NAME
        )
    }
}