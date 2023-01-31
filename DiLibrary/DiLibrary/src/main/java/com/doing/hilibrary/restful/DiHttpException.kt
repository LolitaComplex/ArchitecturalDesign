package com.doing.hilibrary.restful

class DiHttpException(val errorCode: Int, val errorMsg: String, val error: Throwable)
    : Exception(error.message, error) {

    override val message: String
        get() = "DiError code: $errorCode DiError msg: $errorMsg SourceError: ${error.message}"

}