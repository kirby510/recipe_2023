package com.kirby510.recipe.ui.screens.recipe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kirby510.recipe.R
import com.kirby510.recipe.databinding.ItemRecipeListBinding
import com.kirby510.recipe.domain.model.Recipe

class RecipeListAdapter(private var mContext: Context, private var recipeList: MutableList<Recipe> = mutableListOf(), var listener: OnItemClickListener? = null) : RecyclerView.Adapter<RecipeListAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemBinding: ItemRecipeListBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var cvMeal = itemBinding.cvMeal
        var ivMeal = itemBinding.ivMeal
        var tvMeal = itemBinding.tvMeal
        var tvMealCategory = itemBinding.tvMealCategory
        var tvDivider = itemBinding.tvDivider
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

        holder.cvMeal.setOnLongClickListener {
            listener?.onItemLongClick(it, recipe)

            true
        }

        Glide.with(mContext)
            .load(recipe.imageUrl)
            .into(holder.ivMeal)

        holder.tvMeal.text = recipe.name
        holder.tvMealCategory.text = recipe.category
        holder.tvMealArea.text = recipe.area

        if (recipe.imageUrl.isNotEmpty()) {
            holder.tvMeal.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            holder.tvMealCategory.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            holder.tvDivider.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            holder.tvMealArea.setTextColor(ContextCompat.getColor(mContext, R.color.white))
        } else {
            val typedValue = TypedValue()
            mContext.theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
            val colorRes = typedValue.run {
                if (resourceId != 0) {
                    resourceId
                } else {
                    data
                }
            }

            holder.tvMeal.setTextColor(ContextCompat.getColor(mContext, colorRes))
            holder.tvMealCategory.setTextColor(ContextCompat.getColor(mContext, colorRes))
            holder.tvDivider.setTextColor(ContextCompat.getColor(mContext, colorRes))
            holder.tvMealArea.setTextColor(ContextCompat.getColor(mContext, colorRes))
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)

        fun onItemLongClick(view: View, recipe: Recipe)
    }
}