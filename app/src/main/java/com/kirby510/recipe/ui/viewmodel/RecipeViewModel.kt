package com.kirby510.recipe.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.kirby510.recipe.data.model.DataRecipe
import com.kirby510.recipe.domain.model.Recipe
import com.kirby510.recipe.domain.model.RecipeList
import com.kirby510.recipe.domain.repository.APIClient
import com.kirby510.recipe.domain.repository.APIInterface
import com.kirby510.recipe.data.repository.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private var apiInterface: APIInterface? = null
    private var db: AppDatabase? = null

    val recipeList: LiveData<MutableList<Recipe>> get() = _recipeList
    private val _recipeList: MutableLiveData<MutableList<Recipe>> = MutableLiveData<MutableList<Recipe>>()

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
        apiInterface?.getLatestMeals()?.enqueue(object: Callback<RecipeList?> {
            override fun onResponse(
                call: Call<RecipeList?>,
                response: Response<RecipeList?>
            ) {
                Log.i("Recipe", "latest.php")
                Log.i("Recipe", "Code: " + response.code())
                Log.i("Recipe", "Message: " + response.message())
                Log.i("Recipe", "Body: $response")

                insertMealsToDb(response.body()?.meals ?: mutableListOf())
                _recipeList.value = response.body()?.meals
            }

            override fun onFailure(call: Call<RecipeList?>, t: Throwable) {
                t.printStackTrace()

                call.cancel()

                _recipeList.value = getMealsFromDb()
            }
        })
    }

    private fun insertMealsToDb(meals: MutableList<Recipe>) {
        db?.recipeDao()?.deleteAll()

        val recipes: MutableList<DataRecipe> = mutableListOf()
        meals.forEach {
            recipes.add(DataRecipe(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strDrinkAlternate = it.strDrinkAlternate,
                strCategory = it.strCategory,
                strArea = it.strArea,
                strInstructions = it.strInstructions,
                strMealThumb = it.strMealThumb,
                strTags = it.strTags,
                strYoutube = it.strYoutube,
                strIngredient1 = it.strIngredient1,
                strIngredient2 = it.strIngredient2,
                strIngredient3 = it.strIngredient3,
                strIngredient4 = it.strIngredient4,
                strIngredient5 = it.strIngredient5,
                strIngredient6 = it.strIngredient6,
                strIngredient7 = it.strIngredient7,
                strIngredient8 = it.strIngredient8,
                strIngredient9 = it.strIngredient9,
                strIngredient10 = it.strIngredient10,
                strIngredient11 = it.strIngredient11,
                strIngredient12 = it.strIngredient12,
                strIngredient13 = it.strIngredient13,
                strIngredient14 = it.strIngredient14,
                strIngredient15 = it.strIngredient15,
                strIngredient16 = it.strIngredient16,
                strIngredient17 = it.strIngredient17,
                strIngredient18 = it.strIngredient18,
                strIngredient19 = it.strIngredient19,
                strIngredient20 = it.strIngredient20,
                strMeasure1 = it.strMeasure1,
                strMeasure2 = it.strMeasure2,
                strMeasure3 = it.strMeasure3,
                strMeasure4 = it.strMeasure4,
                strMeasure5 = it.strMeasure5,
                strMeasure6 = it.strMeasure6,
                strMeasure7 = it.strMeasure7,
                strMeasure8 = it.strMeasure8,
                strMeasure9 = it.strMeasure9,
                strMeasure10 = it.strMeasure10,
                strMeasure11 = it.strMeasure11,
                strMeasure12 = it.strMeasure12,
                strMeasure13 = it.strMeasure13,
                strMeasure14 = it.strMeasure14,
                strMeasure15 = it.strMeasure15,
                strMeasure16 = it.strMeasure16,
                strMeasure17 = it.strMeasure17,
                strMeasure18 = it.strMeasure18,
                strMeasure19 = it.strMeasure19,
                strMeasure20 = it.strMeasure20,
                strSource = it.strSource,
                strImageSource = it.strImageSource,
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                dateModified = it.dateModified
            ))
        }

        db?.recipeDao()?.insertAll(recipes)
    }

    private fun getMealsFromDb(): MutableList<Recipe> {
        val meals: MutableList<Recipe> = mutableListOf()
        db?.recipeDao()?.getAll()?.forEach {
            val meal = Recipe()
            meal.idMeal = it.idMeal
            meal.strMeal = it.strMeal
            meal.strDrinkAlternate = it.strDrinkAlternate
            meal.strCategory = it.strCategory
            meal.strArea = it.strArea
            meal.strInstructions = it.strInstructions
            meal.strMealThumb = it.strMealThumb
            meal.strTags = it.strTags
            meal.strYoutube = it.strYoutube
            meal.strIngredient1 = it.strIngredient1
            meal.strIngredient2 = it.strIngredient2
            meal.strIngredient3 = it.strIngredient3
            meal.strIngredient4 = it.strIngredient4
            meal.strIngredient5 = it.strIngredient5
            meal.strIngredient6 = it.strIngredient6
            meal.strIngredient7 = it.strIngredient7
            meal.strIngredient8 = it.strIngredient8
            meal.strIngredient9 = it.strIngredient9
            meal.strIngredient10 = it.strIngredient10
            meal.strIngredient11 = it.strIngredient11
            meal.strIngredient12 = it.strIngredient12
            meal.strIngredient13 = it.strIngredient13
            meal.strIngredient14 = it.strIngredient14
            meal.strIngredient15 = it.strIngredient15
            meal.strIngredient16 = it.strIngredient16
            meal.strIngredient17 = it.strIngredient17
            meal.strIngredient18 = it.strIngredient18
            meal.strIngredient19 = it.strIngredient19
            meal.strIngredient20 = it.strIngredient20
            meal.strMeasure1 = it.strMeasure1
            meal.strMeasure2 = it.strMeasure2
            meal.strMeasure3 = it.strMeasure3
            meal.strMeasure4 = it.strMeasure4
            meal.strMeasure5 = it.strMeasure5
            meal.strMeasure6 = it.strMeasure6
            meal.strMeasure7 = it.strMeasure7
            meal.strMeasure8 = it.strMeasure8
            meal.strMeasure9 = it.strMeasure9
            meal.strMeasure10 = it.strMeasure10
            meal.strMeasure11 = it.strMeasure11
            meal.strMeasure12 = it.strMeasure12
            meal.strMeasure13 = it.strMeasure13
            meal.strMeasure14 = it.strMeasure14
            meal.strMeasure15 = it.strMeasure15
            meal.strMeasure16 = it.strMeasure16
            meal.strMeasure17 = it.strMeasure17
            meal.strMeasure18 = it.strMeasure18
            meal.strMeasure19 = it.strMeasure19
            meal.strMeasure20 = it.strMeasure20
            meal.strSource = it.strSource
            meal.strImageSource = it.strImageSource
            meal.strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed
            meal.dateModified = it.dateModified

            meals.add(meal)
        }

        return meals
    }
}