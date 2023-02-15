package com.kirby510.recipe.domain.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        fun getClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v2/9973533/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}