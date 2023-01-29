package com.doing.poetcompiler

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.kotlinpoet.*
import javax.lang.model.element.Modifier

object CodeUtil {

    fun javaFile(packageName: String, tag: String): JavaFile {
        val method = MethodSpec.methodBuilder(PoetCompilerConstant.METHOD_NAME_TEST)
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(TypeName.VOID)
            .addParameter(Array<String>::class.java, "args")
            .addStatement(
                "\$T.out.println(\$S)",
                System::class.java,
                "$tag Doing Hello, Java Poet!"
            )
            .build()

        val classSpec = TypeSpec.classBuilder(PoetCompilerConstant.CLASS_NAME_TEST)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addMethod(method)
            .build()

        return JavaFile.builder(packageName, classSpec).build()
    }

    fun kotlinFile(packageName: String, fileName: String,
           pageList: MutableList<String>): FileSpec {

        return FileSpec.builder(packageName, fileName)
            .addType(
                com.squareup.kotlinpoet.TypeSpec.classBuilder(fileName)
                    .addProperty(
                        PropertySpec.builder("pageList", typeNameOf<ArrayList<String>>())
                            .initializer("%T<String>()", ArrayList::class.java)
                            .addModifiers(KModifier.PRIVATE)
                            .build()
                    )
                    .addInitializerBlock(buildCodeBlock {
                        pageList.forEach { page ->
                            add("this.pageList.add(%S)\n", page)
                        }
                    })
                    .addFunction(
                        FunSpec.builder("getList")
                            .addStatement("return this.pageList")
                            .returns(typeNameOf<MutableList<String>>())
                            .build()
                    )
                    .build()
            ).build()
    }
}