package com.kirby510.recipe.ui.screens.recipe

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kirby510.recipe.R
import com.kirby510.recipe.databinding.ActivityAddEditRecipeBinding
import com.kirby510.recipe.domain.model.Recipe
import com.kirby510.recipe.ui.viewmodel.RecipeViewModel
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class AddEditRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditRecipeBinding
    private lateinit var viewmodel: RecipeViewModel

    companion object {
        const val RECIPE = "recipe"
    }

    private var newBitmapFile: File? = null

    private val imageStoragePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if ((it.isNotEmpty()) && (it.all { it.value })) {
            chooseImage()
        } else {
            val builder = AlertDialog.Builder(this@AddEditRecipeActivity)
            builder.setTitle(R.string.storage_permission)
            builder.setMessage(R.string.storage_permission_error)
            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }
    }

    private val imageChooser = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.let {
                val selectedImageUri = it.data

                if (selectedImageUri != null) {
                    try {
                        val bitmap = if (Build.VERSION.SDK_INT >= 28) {
                            ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, selectedImageUri))
                        } else{
                            @Suppress("DEPRECATION")
                            MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                        }

                        newBitmapFile = File(getExternalFilesDir(null), SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Calendar.getInstance().time) + ".jpg")

                        val fileOutputStream = FileOutputStream(newBitmapFile)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream)
                        fileOutputStream.flush()
                        fileOutputStream.close()

                        Glide.with(this)
                            .load(newBitmapFile?.absoluteFile)
                            .into(binding.ivMeal)
                    } catch (e: IOException) {
                        e.printStackTrace()

                        binding.ivMeal.setImageURI(null)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mtToolbar)

        viewmodel = ViewModelProvider(this)[RecipeViewModel::class.java]

        intent?.let {
            viewmodel.recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializableExtra(RECIPE, Recipe::class.java)
            } else {
                @Suppress("DEPRECATION")
                val recipe = it.getSerializableExtra(RECIPE)

                if (recipe != null) {
                    recipe as Recipe
                } else {
                    null
                }
            }
        }

        loadView()
    }

    fun loadView() {
        viewmodel.recipe?.let {
            binding.etName.setText(it.name)
            binding.etCategory.setText(it.category)
            binding.etArea.setText(it.area)

            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.ivMeal)

            binding.etIngredients.setText(it.ingredients.joinToString("\n"))
            binding.etInstructions.setText(it.instructions.joinToString("\n"))
        }

        binding.btnChooseImage.setOnClickListener {
            chooseImage()
        }

        binding.btnSaveRecipe.setOnClickListener {
            var proceed = true

            binding.etName.error = null
            binding.etCategory.error = null
            binding.etArea.error = null
            binding.etIngredients.error = null
            binding.etInstructions.error = null

            if (binding.etName.text.isNullOrEmpty()) {
                proceed = false

                binding.etName.error = getString(R.string.name_empty)
            }

            if (binding.etCategory.text.isNullOrEmpty()) {
                proceed = false

                binding.etCategory.error = getString(R.string.category_empty)
            }

            if (binding.etArea.text.isNullOrEmpty()) {
                proceed = false

                binding.etArea.error = getString(R.string.area_empty)
            }

            if (binding.etIngredients.text.isNullOrEmpty()) {
                proceed = false

                binding.etIngredients.error = getString(R.string.ingredients_empty)
            }

            if (binding.etInstructions.text.isNullOrEmpty()) {
                proceed = false

                binding.etInstructions.error = getString(R.string.instructions_empty)
            }

            if (proceed) {
                if (viewmodel.recipe != null) {
                    viewmodel.updateMealToDb(viewmodel.recipe!!.id, binding.etName.text.toString(), binding.etCategory.text.toString(), binding.etArea.text.toString(), binding.etIngredients.text.toString(), binding.etInstructions.text.toString(), newBitmapFile?.absolutePath)
                } else {
                    viewmodel.insertMealToDb(binding.etName.text.toString(), binding.etCategory.text.toString(), binding.etArea.text.toString(), binding.etIngredients.text.toString(), binding.etInstructions.text.toString(), newBitmapFile?.absolutePath)
                }

                finish()
            }
        }
    }

    fun chooseImage() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) || (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            imageChooser.launch(Intent.createChooser(intent, getString(R.string.select_picture)))
        } else {
            imageStoragePermission.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }
}