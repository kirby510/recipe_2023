package com.kirby510.recipe.ui.screens.recipe

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kirby510.recipe.databinding.ActivityRecipeBinding
import com.kirby510.recipe.domain.model.Recipe
import com.kirby510.recipe.ui.viewmodel.RecipeViewModel

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var viewmodel: RecipeViewModel

    companion object {
        const val RECIPE = "recipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mtToolbar)

        viewmodel = ViewModelProvider(this)[RecipeViewModel::class.java]

        intent?.let {
            viewmodel.recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializableExtra(RECIPE, Recipe::class.java)
            } else {
                @Suppress("DEPRECATION")
                val recipe = it.getSerializableExtra(RECIPE)

                if (recipe != null) {
                    recipe as Recipe
                } else {
                    null
                }
            }
        }

        loadView()
    }

    fun loadView() {
        Glide.with(this)
            .load(viewmodel.recipe?.imageUrl)
            .into(binding.ivMeal)

        supportActionBar?.title = viewmodel.recipe?.name
        supportActionBar?.subtitle = viewmodel.recipe?.category + " | " + viewmodel.recipe?.area

        binding.tvMealIngredients.text = viewmodel.recipe?.ingredients?.joinToString("\n")
        binding.tvMealInstructions.text = viewmodel.recipe?.instructions?.joinToString("\n")
    }
}