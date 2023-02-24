package com.kirby510.recipe.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.kirby510.recipe.data.model.DataRecipe
import com.kirby510.recipe.domain.model.RecipeListResponse
import com.kirby510.recipe.domain.repository.APIClient
import com.kirby510.recipe.domain.repository.APIInterface
import com.kirby510.recipe.data.repository.AppDatabase
import com.kirby510.recipe.domain.model.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private var apiInterface: APIInterface? = null
    private var db: AppDatabase? = null

    val recipeList: LiveData<MutableList<Recipe>> get() = _recipeList
    private val _recipeList: MutableLiveData<MutableList<Recipe>> = MutableLiveData<MutableList<Recipe>>()

    var recipe: Recipe? = null

    init {
        apiInterface = APIClient.getClient().create(APIInterface::class.java)

        db = Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java, "recipe"
        )
            .allowMainThreadQueries()
            .build()

        _recipeList.value = getMealsFromDb()
    }

    fun getLatestMeals() {
        apiInterface?.getLatestMeals()?.enqueue(object: Callback<RecipeListResponse?> {
            override fun onResponse(
                call: Call<RecipeListResponse?>,
                response: Response<RecipeListResponse?>
            ) {
                Log.i("Recipe", "latest.php")
                Log.i("Recipe", "Code: " + response.code())
                Log.i("Recipe", "Message: " + response.message())
                Log.i("Recipe", "Body: " + response.body())

                val recipeList: MutableList<Recipe> = mutableListOf()
                recipeList.addAll(_recipeList.value ?: mutableListOf())

                response.body()?.meals?.forEach {
                    val recipe = Recipe(it)

                    if (recipeList.indexOfFirst { it.id == recipe.id } < 0) {
                        recipeList.add(Recipe(it))
                    }
                }

                insertMealsToDb(recipeList)
                _recipeList.value = recipeList
            }

            override fun onFailure(call: Call<RecipeListResponse?>, t: Throwable) {
                t.printStackTrace()

                call.cancel()

                getLatestMealsFromDb()
            }
        })
    }

    fun getLatestMealsFromDb() {
        _recipeList.value = getMealsFromDb()
    }

    fun insertMealToDb(name: String, category: String, area: String, ingredients: String, instructions: String, imageUrl: String? = null) {
        val recipe = Recipe()
        recipe.id = UUID.randomUUID().toString()
        recipe.name = name
        recipe.category = category
        recipe.area = area
        recipe.ingredients = ingredients.split("\\n").toMutableList()
        recipe.instructions = instructions.split("\\n").toMutableList()
        recipe.imageUrl = imageUrl ?: ""

        db?.recipeDao()?.insert(recipe.getDataRecipe())

        val tempRecipeList: MutableList<Recipe> = mutableListOf()
        tempRecipeList.addAll(recipeList.value ?: mutableListOf())
        tempRecipeList.add(recipe)

        _recipeList.value = tempRecipeList
    }

    private fun insertMealsToDb(meals: MutableList<Recipe>) {
        db?.recipeDao()?.deleteAll()

        val recipes: MutableList<DataRecipe> = mutableListOf()
        meals.forEach {
            recipes.add(it.getDataRecipe())
        }

        db?.recipeDao()?.insertAll(recipes)
    }

    fun updateMealToDb(id: String, name: String, category: String, area: String, ingredients: String, instructions: String, imageUrl: String? = null) {
        db?.recipeDao()?.update(id, name, category, area, ingredients.split("\\n").toMutableList(), instructions.split("\\n").toMutableList())

        val tempRecipeList: MutableList<Recipe> = mutableListOf()
        tempRecipeList.addAll(recipeList.value ?: mutableListOf())

        val index = tempRecipeList.indexOfFirst { it.id == id }

        if (index >= 0) {
            tempRecipeList[index].name = name
            tempRecipeList[index].category = category
            tempRecipeList[index].area = area
            tempRecipeList[index].ingredients = ingredients.split("\\n").toMutableList()
            tempRecipeList[index].instructions = instructions.split("\\n").toMutableList()
            tempRecipeList[index].imageUrl = imageUrl ?: tempRecipeList[index].imageUrl
        }

        _recipeList.value = tempRecipeList
    }

    private fun getMealsFromDb(): MutableList<Recipe> {
        val meals: MutableList<Recipe> = mutableListOf()
        db?.recipeDao()?.getAll()?.forEach {
            meals.add(Recipe(it))
        }

        return meals
    }

    fun deleteMealFromDb(recipeId: String) {
        db?.recipeDao()?.delete(recipeId)

        _recipeList.value = _recipeList.value?.filter { it.id != recipeId }?.toMutableList()
    }
}