package com.doing.hilibrary.restful

import com.doing.hilibrary.restful.schema.*
import com.doing.hilibrary.restful.schema.Field
import java.lang.reflect.*

class MethodParser(private val baseUrl: String, private val method: Method) {

    private val diRequest = DiRequest()

    fun newRequest(method: Method, args: Array<out Any>?): DiRequest {
        val arguments = args as Array<*>? ?: arrayOf<Array<*>>()
        parseMethodParameters(method, arguments)
        return diRequest
    }

    init {
        // 解析注解
        parseMethodAnnotation(method)

        // 解析方法返回值
        parseMethodReturnType(method)

        // 参数列表属于变化参数，应该是生成Request时解析
        if (diRequest.url.isEmpty()) {
            diRequest.url = baseUrl
        }
    }

    private fun parseMethodParameters(method: Method, arguments: Array<*>) {
        diRequest.parameters.clear()

        val paramAnnotations = method.parameterAnnotations
        require(paramAnnotations.size == arguments.size) {
            String.format("arguments annotations count %s dont match expect count %s",
                paramAnnotations.size, arguments.size)
        }

        paramAnnotations.forEachIndexed { index, anntations ->
            require(anntations.size <= 1) { "filed can only has one annotation :index =$index" }

            val value = arguments[index] as Any
            require(isPrimitive(value)) { "8 basic types are supported for now,index=$index" }

            when (val annotation = anntations[0]) {
                is Field -> {
                    val key = annotation.value
                    diRequest.parameters[key] = value.toString()
                }
                is Path -> {
                    val replaceStr = annotation.path
                    val replacement = value.toString()
                    if (replaceStr.isNotEmpty()) {
                        diRequest.path = diRequest.path.replace("{$replaceStr}", replacement)
                    }
                }
            }
        }
    }


    private fun parseMethodAnnotation(method: Method) {
        val annotations = method.annotations
        annotations.forEach { anotation ->
            when (anotation) {
                is Get -> {
                    diRequest.path = anotation.path
                    diRequest.requestType = DiRequest.Get
                }
                is PostJson -> {
                    diRequest.path = anotation.path
                    diRequest.requestType = DiRequest.PostJson
                }
                is PostForm -> {
                    diRequest.path = anotation.path
                    diRequest.requestType = DiRequest.PostForm
                }
                is Headers -> {
                    anotation.headers.forEach { header ->
                        check(header.contains(DiRequest.HEADER_SPLIT)) {
                            String.format("@headers value must be in the form " +
                                    "[name=value] ,but found [%s]", header)
                        }
                        val data = header.split(DiRequest.HEADER_SPLIT)
                        diRequest.headers[data[0]] = data[1]
                    }
                }
                is BaseUrl -> {
                    diRequest.url = anotation.url
                }
            }
        }
    }

    private fun parseMethodReturnType(method: Method) {
        val type = method.returnType

        if (method.returnType != DiCall::class.java) {
            throw IllegalStateException(
                String.format(
                    "method %s must be type of HiCall.class", method.name)
            )
        }

        val genericReturnType = method.genericReturnType
        if (genericReturnType is ParameterizedType) {
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1) { "method %s can only has one generic return type" }
            val argument = actualTypeArguments[0]
            require(validateGenericType(argument)) {
                String.format("method %s generic return type must not be an unknown type. " + method.name)
            }
            diRequest.returnType = argument
        } else {
            throw IllegalStateException(String.format(
                "method %s must has one gerneric return type", method.name))
        }
    }

    private fun validateGenericType(type: Type): Boolean {
        /**
         *wrong
         *  fun test():HiCall<Any>
         *  fun test():HiCall<List<*>>
         *  fun test():HiCall<ApiInterface>
         *expect
         *  fun test():HiCall<User>
         */
        //如果指定的泛型是集合类型的，那还检查集合的泛型参数
        if (type is GenericArrayType) {
            return validateGenericType(type.genericComponentType)
        }
        //如果指定的泛型是一个接口 也不行
        if (type is TypeVariable<*>) {
            return false
        }
        //如果指定的泛型是一个通配符 ？extends Number 也不行
        if (type is WildcardType) {
            return false
        }

        return true
    }

    private fun isPrimitive(value: Any): Boolean {
        //String
        if (value.javaClass == String::class.java) {
            return true
        }
        try {
            //int byte short long boolean char double float
            val field = value.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return false
    }

}