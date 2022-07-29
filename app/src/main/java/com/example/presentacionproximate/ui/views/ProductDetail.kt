package com.example.presentacionproximate.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.presentacionproximate.R
import com.example.presentacionproximate.databinding.ActivityProductDetailBinding
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetail : AppCompatActivity() {

    lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.extras != null) {
            Glide.with(this).load(intent.getStringExtra("imageUrl")).into(fotoAmpliada)
        }

        if (intent.extras != null) {
            val descrip: Intent = intent
            var mostrarDescr = descrip.getStringExtra("descripUrl")
            descriptionAmpliada.text = "$mostrarDescr"
        }

        if (intent.extras != null) {
            val titu: Intent = intent
            var mostrartitu = titu.getStringExtra("titulo")
            tituloxml.text = "$mostrartitu"
        }
    }
}
