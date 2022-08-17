package com.example.presentacionproximate.ui.views

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.presentacionproximate.R
import com.example.presentacionproximate.databinding.ActivityCamera2Binding
import com.example.presentacionproximate.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    lateinit var binding: ActivityCamera2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCamera2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamara.setOnClickListener{startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))}

    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            val imageBitMap = intent?.extras?.get("data") as Bitmap
            val imageView =  binding.imageFotoCamera
            imageView.setImageBitmap(imageBitMap)
        }
    }
}