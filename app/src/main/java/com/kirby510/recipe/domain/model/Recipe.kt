package com.kirby510.recipe.domain.model

import com.kirby510.recipe.data.model.DataRecipe
import java.io.Serializable

class Recipe : Serializable {
    constructor()

    constructor(recipeResponse: RecipeResponse) {
        id = recipeResponse.idMeal ?: ""
        name = recipeResponse.strMeal ?: ""
        category = recipeResponse.strCategory ?: ""
        area = recipeResponse.strArea ?: ""

        ingredients.clear()

        if (!recipeResponse.strIngredient1.isNullOrEmpty() && !recipeResponse.strMeasure1.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient1 + " (" + recipeResponse.strMeasure1 + ")")
        if (!recipeResponse.strIngredient2.isNullOrEmpty() && !recipeResponse.strMeasure2.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient2 + " (" + recipeResponse.strMeasure2 + ")")
        if (!recipeResponse.strIngredient3.isNullOrEmpty() && !recipeResponse.strMeasure3.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient3 + " (" + recipeResponse.strMeasure3 + ")")
        if (!recipeResponse.strIngredient4.isNullOrEmpty() && !recipeResponse.strMeasure4.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient4 + " (" + recipeResponse.strMeasure4 + ")")
        if (!recipeResponse.strIngredient5.isNullOrEmpty() && !recipeResponse.strMeasure5.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient5 + " (" + recipeResponse.strMeasure5 + ")")
        if (!recipeResponse.strIngredient6.isNullOrEmpty() && !recipeResponse.strMeasure6.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient6 + " (" + recipeResponse.strMeasure6 + ")")
        if (!recipeResponse.strIngredient7.isNullOrEmpty() && !recipeResponse.strMeasure7.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient7 + " (" + recipeResponse.strMeasure7 + ")")
        if (!recipeResponse.strIngredient8.isNullOrEmpty() && !recipeResponse.strMeasure8.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient8 + " (" + recipeResponse.strMeasure8 + ")")
        if (!recipeResponse.strIngredient9.isNullOrEmpty() && !recipeResponse.strMeasure9.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient9 + " (" + recipeResponse.strMeasure9 + ")")
        if (!recipeResponse.strIngredient10.isNullOrEmpty() && !recipeResponse.strMeasure10.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient10 + " (" + recipeResponse.strMeasure10 + ")")
        if (!recipeResponse.strIngredient11.isNullOrEmpty() && !recipeResponse.strMeasure11.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient11 + " (" + recipeResponse.strMeasure11 + ")")
        if (!recipeResponse.strIngredient12.isNullOrEmpty() && !recipeResponse.strMeasure12.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient12 + " (" + recipeResponse.strMeasure12 + ")")
        if (!recipeResponse.strIngredient13.isNullOrEmpty() && !recipeResponse.strMeasure13.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient13 + " (" + recipeResponse.strMeasure13 + ")")
        if (!recipeResponse.strIngredient14.isNullOrEmpty() && !recipeResponse.strMeasure14.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient14 + " (" + recipeResponse.strMeasure14 + ")")
        if (!recipeResponse.strIngredient15.isNullOrEmpty() && !recipeResponse.strMeasure15.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient15 + " (" + recipeResponse.strMeasure15 + ")")
        if (!recipeResponse.strIngredient16.isNullOrEmpty() && !recipeResponse.strMeasure16.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient16 + " (" + recipeResponse.strMeasure16 + ")")
        if (!recipeResponse.strIngredient17.isNullOrEmpty() && !recipeResponse.strMeasure17.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient17 + " (" + recipeResponse.strMeasure17 + ")")
        if (!recipeResponse.strIngredient18.isNullOrEmpty() && !recipeResponse.strMeasure18.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient18 + " (" + recipeResponse.strMeasure18 + ")")
        if (!recipeResponse.strIngredient19.isNullOrEmpty() && !recipeResponse.strMeasure19.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient19 + " (" + recipeResponse.strMeasure19 + ")")
        if (!recipeResponse.strIngredient20.isNullOrEmpty() && !recipeResponse.strMeasure20.isNullOrEmpty()) ingredients.add(recipeResponse.strIngredient20 + " (" + recipeResponse.strMeasure20 + ")")

        instructions = recipeResponse.strInstructions?.split("\\r\\n")?.toMutableList() ?: mutableListOf()
        imageUrl = recipeResponse.strMealThumb ?: ""
    }

    constructor(dataRecipe: DataRecipe) {
        id = dataRecipe.recipeId
        name = dataRecipe.name
        category = dataRecipe.category
        area = dataRecipe.area
        ingredients = dataRecipe.ingredients
        instructions = dataRecipe.instructions
        imageUrl = dataRecipe.imageUrl
    }

    fun getDataRecipe(): DataRecipe {
        return DataRecipe(
            recipeId = id,
            name = name,
            category = category,
            area = area,
            ingredients = ingredients,
            instructions = instructions,
            imageUrl = imageUrl
        )
    }

    var id = ""
    var name = ""
    var category = ""
    var area = ""
    var ingredients: MutableList<String> = mutableListOf()
    var instructions: MutableList<String> = mutableListOf()
    var imageUrl = ""
}