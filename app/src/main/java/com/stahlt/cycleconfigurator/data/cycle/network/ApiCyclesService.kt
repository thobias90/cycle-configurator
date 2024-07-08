package com.stahlt.cycleconfigurator.data.cycle.network

import com.stahlt.cycleconfigurator.data.cycle.Cycle
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "http://10.0.2.2:8080"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ApiCyclesService {
    @GET("cycles")
    suspend fun findAll(): List<Cycle>

    @GET("cycles/{id}")
    suspend fun findById(id: Int): Cycle

    @DELETE("cycles/{id}")
    suspend fun delete(id: Int)

    @POST("cycles")
    suspend fun save(@Body cycle: Cycle): Response<Cycle>
}

object ApiCycles {
    val retrofitService: ApiCyclesService by lazy {
        retrofit.create(ApiCyclesService::class.java)
    }
}