package com.kirby510.recipe.ui.screens.recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kirby510.recipe.domain.model.Recipe
import com.kirby510.recipe.databinding.ActivityRecipeListBinding
import com.kirby510.recipe.ui.screens.recipe.adapter.RecipeListAdapter
import com.kirby510.recipe.ui.viewmodel.RecipeViewModel

class RecipeListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var viewmodel: RecipeViewModel

    private var adapter: RecipeListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mtToolbar)

        viewmodel = ViewModelProvider(this)[RecipeViewModel::class.java]

        loadView()
        getLatestMeals()
    }

    fun loadView() {
        binding.sflRecipeList.setOnRefreshListener {
            getLatestMeals()
        }

        loadAdapter()
    }

    fun getLatestMeals() {
        viewmodel.recipeList.observe(this) {
            loadAdapter(it)
        }
        viewmodel.getLatestMeals()
    }

    fun loadAdapter(recipeList: MutableList<Recipe> = mutableListOf()) {
        binding.rvRecipeList.adapter = RecipeListAdapter(this, recipeList, object: RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                startActivity(Intent(this@RecipeListActivity, RecipeActivity::class.java)
                    .putExtra("recipe", recipe))
            }
        })

        binding.sflRecipeList.isRefreshing = false
    }
}