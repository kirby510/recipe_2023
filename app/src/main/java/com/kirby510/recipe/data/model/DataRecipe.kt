package com.kirby510.recipe.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kirby510.recipe.data.repository.StringListConverters

@Entity(tableName = "recipe")
data class DataRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "recipeId") val recipeId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "area") val area: String,
    @ColumnInfo(name = "ingredients")
    @TypeConverters(StringListConverters::class) val ingredients: MutableList<String>,
    @ColumnInfo(name = "instructions")
    @TypeConverters(StringListConverters::class) val instructions: MutableList<String>,
    @ColumnInfo(name = "imageUrl") val imageUrl: String
)