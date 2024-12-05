package com.example.rasanusa.data.api


import com.example.rasanusa.data.response.FoodResponse
import com.example.rasanusa.data.response.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("makanan")
    fun getAllFood(): Call<FoodResponse>

//    @GET("makanan/{name}")
//    fun getDetail(
//        @Path("name") name: String? = null,
//    ) : Call<FoodResponse>

    @Multipart
    @POST("predict")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<PredictResponse>

//    @GET("result")
//    fun getAllResult(): Call<ResultResponse>


}