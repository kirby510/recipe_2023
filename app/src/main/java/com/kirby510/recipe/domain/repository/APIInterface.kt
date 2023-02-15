package com.kirby510.recipe.domain.repository

import com.kirby510.recipe.domain.model.RecipeList
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("latest.php")
    fun getLatestMeals(): Call<RecipeList?>?
}