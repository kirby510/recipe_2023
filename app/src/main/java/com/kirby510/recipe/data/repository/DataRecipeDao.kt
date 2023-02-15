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
    fun insertAll(recipeList: List<DataRecipe>)

    @Query("DELETE FROM recipe")
    fun deleteAll()
}