package com.example.presentacionproximate.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.presentacionproximate.databinding.ActivityProductDetailBinding

class ProductDetail : AppCompatActivity() {

    lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.extras != null) {

            Glide.with(this).load(intent.getStringExtra("imageUrl")).into(binding.fotoAmpliada)

            var showDescription = intent.getStringExtra("descripUrl")

            if (showDescription != null && showDescription.isNotEmpty()) {
                    binding.descriptionAmpliada.text = "$showDescription"
            }

            var showTitle = intent.getStringExtra("titulo")

            if (showTitle != null && showTitle.isNotEmpty()) {
                    binding.tituloxml.text = "$showTitle"
            }
        }
    }
}
