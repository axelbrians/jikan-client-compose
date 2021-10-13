package com.machina.jikan_client_compose.data.utils

import com.machina.jikan_client_compose.core.error.GeneralError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class ErrorConverter @Inject constructor(
    private val retrofit: Retrofit
) {

    fun convertBasicError(error: ResponseBody): GeneralError? {
        val errorConverter: Converter<ResponseBody, GeneralError> = retrofit
            .responseBodyConverter(GeneralError::class.java, arrayOfNulls<Annotation>(0))
        // Convert the error body into our Error type.
        return errorConverter.convert(error)
    }
}