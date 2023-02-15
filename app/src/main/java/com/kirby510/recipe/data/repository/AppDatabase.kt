package com.kirby510.recipe.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kirby510.recipe.data.model.DataRecipe

@Database(entities = [DataRecipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): DataRecipeDao
}