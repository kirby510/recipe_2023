package com.kirby510.recipe.domain.model

import com.google.gson.annotations.SerializedName

class RecipeList {
    @SerializedName("meals")
    var meals: MutableList<Recipe>? = null
}