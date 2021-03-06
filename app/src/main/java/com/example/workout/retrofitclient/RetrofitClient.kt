package com.example.workout.retrofitclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        fun getClient() : Retrofit {
            val BASE_URL = "https://newsapi.org/v2/"
            val retrofit: Retrofit = Retrofit.Builder().
            baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }
}