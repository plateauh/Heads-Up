package com.najed.headsupv2

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @POST("/celebrities/")
    fun addCeleb(@Body data: CelebItem): Call<CelebItem?>?

    @GET("/celebrities/")
    fun getCelebs(): Call<Celeb?>?

    @PUT("/celebrities/{id}")
    fun updateCeleb(@Path("id") id: Int, @Body celebData: CelebItem): Call<CelebItem>

    @DELETE("/celebrities/{id}")
    fun deleteCeleb(@Path("id") id: Int): Call<Void>?
}