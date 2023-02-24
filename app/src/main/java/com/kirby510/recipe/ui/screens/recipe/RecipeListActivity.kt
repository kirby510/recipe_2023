package com.kirby510.recipe.ui.screens.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.kirby510.recipe.R
import com.kirby510.recipe.databinding.ActivityRecipeListBinding
import com.kirby510.recipe.domain.model.Recipe
import com.kirby510.recipe.ui.screens.recipe.adapter.RecipeListAdapter
import com.kirby510.recipe.ui.viewmodel.RecipeViewModel

class RecipeListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var viewmodel: RecipeViewModel

    private var adapter: RecipeListAdapter? = null
    private var firstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mtToolbar)

        viewmodel = ViewModelProvider(this)[RecipeViewModel::class.java]

        loadView()
    }

    override fun onResume() {
        super.onResume()

        if (firstLoad) {
            viewmodel.getLatestMeals()
        } else {
            viewmodel.getLatestMealsFromDb()
        }
    }

    fun loadView() {
        binding.sflRecipeList.setOnRefreshListener {
            viewmodel.getLatestMeals()
        }

        viewmodel.recipeList.observe(this) {
            firstLoad = false

            loadAdapter(it)
        }

        loadAdapter()

        binding.fabCreate.setOnClickListener {
            startActivity(Intent(this@RecipeListActivity, AddEditRecipeActivity::class.java))
        }
    }

    fun loadAdapter(recipeList: MutableList<Recipe> = mutableListOf()) {
        adapter = RecipeListAdapter(this, recipeList, object: RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                startActivity(Intent(this@RecipeListActivity, RecipeActivity::class.java)
                    .putExtra(RecipeActivity.RECIPE, recipe))
            }

            override fun onItemLongClick(view: View, recipe: Recipe) {
                val popupMenu = PopupMenu(this@RecipeListActivity, view)
                popupMenu.menuInflater.inflate(R.menu.popup_menu_recipe_list, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.recipe_edit -> {
                            startActivity(Intent(this@RecipeListActivity, AddEditRecipeActivity::class.java)
                                .putExtra(AddEditRecipeActivity.RECIPE, recipe))
                        }
                        R.id.recipe_delete -> {
                            popupMenu.dismiss()

                            val builder = AlertDialog.Builder(this@RecipeListActivity)
                            builder.setTitle(R.string.are_you_sure)
                            builder.setMessage(R.string.delete_confirmation)
                            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                                dialog.dismiss()

                                viewmodel.deleteMealFromDb(recipe.id)
                            }
                            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                                dialog.dismiss()
                            }
                            builder.show()
                        }
                        R.id.recipe_cancel -> {
                            popupMenu.dismiss()
                        }
                    }

                    true
                }

                popupMenu.show()
            }
        })
        binding.rvRecipeList.adapter = adapter

        binding.sflRecipeList.isRefreshing = false
    }
}