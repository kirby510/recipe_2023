package com.kirby510.recipe.ui.screens.recipe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kirby510.recipe.domain.model.Recipe
import com.kirby510.recipe.databinding.ItemRecipeListBinding

class RecipeListAdapter(private var mContext: Context, private var recipeList: MutableList<Recipe>, var listener: OnItemClickListener? = null) : RecyclerView.Adapter<RecipeListAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemBinding: ItemRecipeListBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var cvMeal = itemBinding.cvMeal
        var ivMeal = itemBinding.ivMeal
        var tvMeal = itemBinding.tvMeal
        var tvMealCategory = itemBinding.tvMealCategory
        var tvMealArea = itemBinding.tvMealArea
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val recipe = recipeList[position]

        holder.cvMeal.setOnClickListener {
            listener?.onItemClick(recipe)
        }

        Glide.with(mContext)
            .load(recipe.strMealThumb)
            .into(holder.ivMeal)

        holder.tvMeal.text = recipe.strMeal
        holder.tvMealCategory.text = recipe.strCategory
        holder.tvMealArea.text = recipe.strArea
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)
    }
}