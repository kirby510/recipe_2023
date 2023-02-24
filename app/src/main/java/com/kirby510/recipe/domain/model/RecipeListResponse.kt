package com.kirby510.recipe.domain.model

import com.google.gson.annotations.SerializedName

class RecipeListResponse {
    @SerializedName("meals")
    var meals: MutableList<RecipeResponse>? = null
}