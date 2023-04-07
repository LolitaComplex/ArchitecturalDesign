package com.doing.diproject.net

import com.doing.dicommon.component.DiBaseApplication
import com.doing.diproject.common.AppApplication
import com.doing.hilibrary.restful.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import java.lang.reflect.Type

class RetrofitFactory(baseUrl: String) : DiCall.Factory {

    private val mApiService: ApiService
    private val mGsonConvert = GsonConvert()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder()
                .cache(Cache(AppApplication.application.cacheDir, 1024 * 1024 * 10))
                .build())
            .build()

        mApiService = retrofit.create(ApiService::class.java)
    }

    override fun newCall(request: DiRequest): DiCall<*> {
        return RetrofitCall<Any>(request)
    }

    private inner class RetrofitCall<T>(private val request: DiRequest): DiCall<T> {


        override fun execute(): DiResponse<T> {
            val headers = request.headers
            val realCall = buildRequest(headers)
            val response = try {
                realCall.execute()
            } catch (e: Exception) {
                throw DiHttpException(-1, "real call execute error", e)
            }
            return parseResponse(mGsonConvert, response, request.returnType)
        }

        override fun enqueue(callback: DiCallback<T>) {
            val headers = request.headers
            val realCall = buildRequest(headers)
            realCall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>,
                    response: Response<ResponseBody>) {
                    callback.onSuccess(parseResponse(mGsonConvert,
                        response, request.returnType))
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback.onFailure(DiHttpException(-1, "real call enqueue error", t))
                }
            })
        }

        private fun buildRequest(headers: MutableMap<String, String>): Call<ResponseBody> {
            return when (request.requestType) {
                DiRequest.Get -> {
                    mApiService.get(headers, request.path, request.parameters)
                }
                DiRequest.PostJson -> {
                    val obj = JSONObject()
                    request.parameters.forEach { entry ->
                        obj.put(entry.key, entry.value)
                    }
                    val mediaType = "application/json;charset=utf-8".toMediaType()
                    val requestBody = obj.toString().toRequestBody(mediaType)

                    mApiService.post(headers, request.path, requestBody)
                }
                DiRequest.PostForm -> {
                    val builder = FormBody.Builder()
                    request.parameters.forEach { entry ->
                        builder.add(entry.key, entry.value)
                    }
                    mApiService.post(headers, request.path, builder.build())
                }
                else -> {
                    throw IllegalStateException(
                        String.format("request type: %s not support", request.requestType)
                    )
                }
            }
        }

        private fun parseResponse(convert: DiConvert, response: Response<ResponseBody>, type: Type): DiResponse<T> {
            val body = if (response.isSuccessful) {
                response.body()
            } else {
                response.errorBody()
            }

            val rawContent = body!!.string().toString()
            return convert.convert<T>(rawContent, type)
        }

    }

    interface ApiService {

        @GET
        fun get(@HeaderMap headers: MutableMap<String, String>?, @Url url: String,
            @QueryMap(encoded = true) params: MutableMap<String, String>?
        ): Call<ResponseBody>

        @POST
        fun post(@HeaderMap headers: MutableMap<String, String>?, @Url url: String,
            @Body body: RequestBody?
        ): Call<ResponseBody>

//        @PUT
//        fun put(@HeaderMap headers: MutableMap<String, String>?, @Url url: String,
//            @Body body: RequestBody?
//        ): Call<ResponseBody>
//
//        @DELETE//不可以携带requestbody
//        fun delete(@HeaderMap headers: MutableMap<String, String>?,
//            @Url url: String
//        ): Call<ResponseBody>
    }

}