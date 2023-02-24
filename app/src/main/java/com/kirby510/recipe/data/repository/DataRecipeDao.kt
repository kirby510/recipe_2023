package com.kirby510.recipe.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kirby510.recipe.data.model.DataRecipe

@Dao
interface DataRecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAll(): List<DataRecipe>

    @Insert
    fun insert(recipe: DataRecipe)

    @Insert
    fun insertAll(recipeList: List<DataRecipe>)

    @Query("UPDATE recipe SET name = :name, category = :category, area = :area, ingredients = :ingredients, instructions = :instructions WHERE recipeId = :recipeId")
    fun update(recipeId: String, name: String, category: String, area: String, ingredients: MutableList<String>, instructions: MutableList<String>)

    @Query("DELETE FROM recipe WHERE recipeId = :recipeId")
    fun delete(recipeId: String)

    @Query("DELETE FROM recipe")
    fun deleteAll()
}