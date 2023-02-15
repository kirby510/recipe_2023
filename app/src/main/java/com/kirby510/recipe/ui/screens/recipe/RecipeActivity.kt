package com.kirby510.recipe.ui.screens.recipe

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kirby510.recipe.domain.model.Recipe
import com.kirby510.recipe.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding

    private var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mtToolbar)

        intent?.let {
            recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializableExtra("recipe", Recipe::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getSerializableExtra("recipe") as Recipe
            }
        }

        loadView()
    }

    fun loadView() {
        Glide.with(this)
            .load(recipe?.strMealThumb)
            .into(binding.ivMeal)

        supportActionBar?.title = recipe?.strMeal
        supportActionBar?.subtitle = recipe?.strCategory + " | " + recipe?.strArea

        binding.tvMealIngredients.text = getIngredients()
        binding.tvMealInstructions.text = getInstructions()
    }

    fun getIngredients() : String {
        var ingredientStr = ""

        if (!recipe?.strIngredient1.isNullOrEmpty() && !recipe?.strMeasure1.isNullOrEmpty()) ingredientStr += recipe?.strIngredient1 + " (" + recipe?.strMeasure1 + ")" + "\n"
        if (!recipe?.strIngredient2.isNullOrEmpty() && !recipe?.strMeasure2.isNullOrEmpty()) ingredientStr += recipe?.strIngredient2 + " (" + recipe?.strMeasure2 + ")" + "\n"
        if (!recipe?.strIngredient3.isNullOrEmpty() && !recipe?.strMeasure3.isNullOrEmpty()) ingredientStr += recipe?.strIngredient3 + " (" + recipe?.strMeasure3 + ")" + "\n"
        if (!recipe?.strIngredient4.isNullOrEmpty() && !recipe?.strMeasure4.isNullOrEmpty()) ingredientStr += recipe?.strIngredient4 + " (" + recipe?.strMeasure4 + ")" + "\n"
        if (!recipe?.strIngredient5.isNullOrEmpty() && !recipe?.strMeasure5.isNullOrEmpty()) ingredientStr += recipe?.strIngredient5 + " (" + recipe?.strMeasure5 + ")" + "\n"
        if (!recipe?.strIngredient6.isNullOrEmpty() && !recipe?.strMeasure6.isNullOrEmpty()) ingredientStr += recipe?.strIngredient6 + " (" + recipe?.strMeasure6 + ")" + "\n"
        if (!recipe?.strIngredient7.isNullOrEmpty() && !recipe?.strMeasure7.isNullOrEmpty()) ingredientStr += recipe?.strIngredient7 + " (" + recipe?.strMeasure7 + ")" + "\n"
        if (!recipe?.strIngredient8.isNullOrEmpty() && !recipe?.strMeasure8.isNullOrEmpty()) ingredientStr += recipe?.strIngredient8 + " (" + recipe?.strMeasure8 + ")" + "\n"
        if (!recipe?.strIngredient9.isNullOrEmpty() && !recipe?.strMeasure9.isNullOrEmpty()) ingredientStr += recipe?.strIngredient9 + " (" + recipe?.strMeasure9 + ")" + "\n"
        if (!recipe?.strIngredient10.isNullOrEmpty() && !recipe?.strMeasure10.isNullOrEmpty()) ingredientStr += recipe?.strIngredient10 + " (" + recipe?.strMeasure10 + ")" + "\n"
        if (!recipe?.strIngredient11.isNullOrEmpty() && !recipe?.strMeasure11.isNullOrEmpty()) ingredientStr += recipe?.strIngredient11 + " (" + recipe?.strMeasure11 + ")" + "\n"
        if (!recipe?.strIngredient12.isNullOrEmpty() && !recipe?.strMeasure12.isNullOrEmpty()) ingredientStr += recipe?.strIngredient12 + " (" + recipe?.strMeasure12 + ")" + "\n"
        if (!recipe?.strIngredient13.isNullOrEmpty() && !recipe?.strMeasure13.isNullOrEmpty()) ingredientStr += recipe?.strIngredient13 + " (" + recipe?.strMeasure13 + ")" + "\n"
        if (!recipe?.strIngredient14.isNullOrEmpty() && !recipe?.strMeasure14.isNullOrEmpty()) ingredientStr += recipe?.strIngredient14 + " (" + recipe?.strMeasure14 + ")" + "\n"
        if (!recipe?.strIngredient15.isNullOrEmpty() && !recipe?.strMeasure15.isNullOrEmpty()) ingredientStr += recipe?.strIngredient15 + " (" + recipe?.strMeasure15 + ")" + "\n"
        if (!recipe?.strIngredient16.isNullOrEmpty() && !recipe?.strMeasure16.isNullOrEmpty()) ingredientStr += recipe?.strIngredient16 + " (" + recipe?.strMeasure16 + ")" + "\n"
        if (!recipe?.strIngredient17.isNullOrEmpty() && !recipe?.strMeasure17.isNullOrEmpty()) ingredientStr += recipe?.strIngredient17 + " (" + recipe?.strMeasure17 + ")" + "\n"
        if (!recipe?.strIngredient18.isNullOrEmpty() && !recipe?.strMeasure18.isNullOrEmpty()) ingredientStr += recipe?.strIngredient18 + " (" + recipe?.strMeasure18 + ")" + "\n"
        if (!recipe?.strIngredient19.isNullOrEmpty() && !recipe?.strMeasure19.isNullOrEmpty()) ingredientStr += recipe?.strIngredient19 + " (" + recipe?.strMeasure19 + ")" + "\n"
        if (!recipe?.strIngredient20.isNullOrEmpty() && !recipe?.strMeasure20.isNullOrEmpty()) ingredientStr += recipe?.strIngredient20 + " (" + recipe?.strMeasure20 + ")" + "\n"

        return ingredientStr.trim()
    }

    fun getInstructions() : String {
        return recipe?.strInstructions?.split("\\r\\n")?.joinToString("\n") ?: ""
    }
}