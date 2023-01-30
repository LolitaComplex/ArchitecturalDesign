package com.doing.poetcompiler

import com.doing.navigatorannotation.AddJavaCode
import com.doing.navigatorannotation.AddKotlinCode
import com.doing.navigatorannotation.Destination
import com.google.auto.service.AutoService
import com.sun.source.tree.Tree
import com.sun.tools.javac.api.JavacTrees
import com.sun.tools.javac.processing.JavacProcessingEnvironment
import com.sun.tools.javac.tree.JCTree
import com.sun.tools.javac.tree.TreeMaker
import com.sun.tools.javac.tree.TreeScanner
import com.sun.tools.javac.util.Names
import java.io.File
import java.io.FileOutputStream
import java.util.ArrayList
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic
import javax.tools.StandardLocation

@AutoService(Processor::class)
class PoetProcessor : AbstractProcessor() {

    companion object {
        var count = 0
    }

    private lateinit var TAG :String
    private lateinit var mFiler: Filer
    private var mModuleName = ""
    private var isApp = false
    private var mPoetName: String? = null

    private lateinit var mJcTree: JavacTrees
    private lateinit var mTreeMaker: TreeMaker
    private lateinit var mNames: Names

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)

        val moduleName = processingEnv.options[PoetCompilerConstant.OPTION_MODULE_NAME]
        this.TAG = "$moduleName >>> ${PoetCompilerConstant.TAG}"

        this.mFiler = processingEnv.filer
        this.mModuleName = moduleName ?: ""
        this.isApp = processingEnv.options[PoetCompilerConstant.OPTION_IS_APP] == "true"
        this.mPoetName = processingEnv.options[PoetCompilerConstant.OPTION_POET_NAME]
        println("$TAG init SourceVersion: ${processingEnv.sourceVersion}" +
                " evn hashcode: ${processingEnv.hashCode()} count: ${count++}")

        println("$TAG 哈哈哈哈 和和IEhi诶")
        processingEnv.messager.printMessage(Diagnostic.Kind.OTHER, "========================")

        val context = (processingEnv as JavacProcessingEnvironment).context

        this.mJcTree = JavacTrees.instance(processingEnv)
        this.mTreeMaker = TreeMaker.instance(context)
        this.mNames = Names.instance(context)
        println("$TAG JavacTree: $mJcTree TreeMaker: $mTreeMaker Names: $mNames")
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val filer = mFiler
        val moduleName = mModuleName
        val targetModule = mPoetName

        createCodeDataFile(roundEnv, filer, moduleName, targetModule)

        val javaElements = roundEnv?.getElementsAnnotatedWith(AddJavaCode::class.java)
        addJavaCode(javaElements)

        return false
    }

    private fun createCodeDataFile(roundEnv: RoundEnvironment?, filer: Filer,
        moduleName: String, targetModule: String?): Boolean {
        val elements = roundEnv?.getElementsAnnotatedWith(Destination::class.java)

        if (elements == null || elements.isEmpty()) {
            return true
        }

        val pageList = mutableListOf<String>()
        elements.forEach { element ->
            val typeElement = element as TypeElement
            val className = typeElement.qualifiedName
            pageList.add(className.toString())
        }

        val outputFile = getTempFile(filer, moduleName)
        if (!outputFile.exists()) {
            outputFile.createNewFile()
        }
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
            makeKotlinFile(filer, moduleName, targetModule, pageList)


        } else if (!isApp) {
            FileOutputStream(outputFile, true).bufferedWriter().use { writer ->
                pageList.forEach { line ->
                    writer.write("${line}\n")
                }
                writer.flush()
            }
        }
        return false
    }

    private fun addJavaCode(javaElements: Set<Element>?) {
        println("$TAG addJavaCode elements size: ${javaElements?.size}")
        if (javaElements == null || javaElements.isEmpty()) {
            return
        }
        val jcTree = mJcTree
        val maker = mTreeMaker

        javaElements.forEach { element ->
            if (!element.kind.isClass) {
                return@forEach
            }

            val typeElement = element as TypeElement
            val classDec = jcTree.getTree(typeElement)
            val methods = mutableListOf<JCTree.JCMethodDecl>()

            classDec.accept(object : TreeScanner() {
                override fun visitClassDef(tree: JCTree.JCClassDecl?) {

                    var method : JCTree.JCMethodDecl? = null
                    tree?.defs?.forEach { item ->
                        if (item.kind == Tree.Kind.METHOD) {
                            val itemMethod = item as JCTree.JCMethodDecl
                            if (itemMethod.name.toString() == "testJavaCode") {
                                method = itemMethod
                            }
                            println("$TAG: AddJavaCode method name:" +
                                    " ${method?.getName()}")
                        }
                    }
                    if (method != null) {

                    }
                    super.visitClassDef(tree)
                }

                override fun visitMethodDef(p0: JCTree.JCMethodDecl?) {
                    super.visitMethodDef(p0)


                }
            })
        }
    }

    private fun makeKotlinFile(filer: Filer, moduleName: String, targetModule: String,
           pageList: MutableList<String>) {
        println("$TAG process: kotlin List size: ${pageList.size}")

        val packageName = "com.doing.poet.kotlin"
        val fileName = "PageModel"
        val kotlinFile = CodeUtil.kotlinFile(packageName, fileName, pageList)
        val targetFile = getTargetFile(filer, packageName, "${fileName}.kt", moduleName, targetModule)

        kotlinFile.writeTo(targetFile)
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

        val resource = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", fileName)
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
        return mutableSetOf(
            Destination::class.java.canonicalName,
            AddJavaCode::class.java.canonicalName,
            AddKotlinCode::class.java.canonicalName
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedOptions(): MutableSet<String> {
        return mutableSetOf(
            PoetCompilerConstant.OPTION_MODULE_NAME,
            PoetCompilerConstant.OPTION_IS_APP,
            PoetCompilerConstant.OPTION_POET_NAME,
            "AROUTER_MODULE_NAME"
        )
    }
}