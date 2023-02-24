package com.kirby510.recipe.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kirby510.recipe.data.model.DataRecipe

@Database(entities = [DataRecipe::class], version = 1)
@TypeConverters(StringListConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): DataRecipeDao
}