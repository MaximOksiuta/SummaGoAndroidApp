package com.sirius.siriussummago.data.api

import android.util.Log
import com.sirius.siriussummago.common.Constants
import com.sirius.siriussummago.data.datamodels.CheckTokenResponseModel
import com.sirius.siriussummago.data.datamodels.SignUpRequestModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json

object SummaGoService {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun checkToken(token: String): Boolean {
        Log.d("SummaGoService", "Send request to check token $token")
        val response = client.post(Constants.BASE_API_URL + "/user/check") {
            header("token", token)
        }

        if (response.status.isSuccess()) {
            return response.body<CheckTokenResponseModel>().exist
        } else {
            throw Exception(response.status.description)
        }
    }

    suspend fun signUp(token: String, name: String, organization: String, role: String) {
        Log.d("SummaGoService", "signUp with name:$name organization:$organization role:$role")

        val response = client.post(Constants.BASE_API_URL + "/user/register") {
            header("token", token)
            contentType(ContentType.Application.Json)
            setBody(
                SignUpRequestModel(
                    name = name, workplace = organization, job = role
                )
            )
        }

        Log.d("SummaGoService", response.status.toString())

        if (!response.status.isSuccess()) {
            Log.d("SummaGoService", response.status.description)
            throw Exception(response.status.description)
        } else{
            Log.d("SummaGoService", "signUp success")
        }
    }


}