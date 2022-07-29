package com.example.presentacionproximate.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.presentacionproximate.Home
import com.example.presentacionproximate.databinding.ActivityQuejasyReclamosBinding

class QuejasyReclamos : AppCompatActivity() {

     lateinit var binding: ActivityQuejasyReclamosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuejasyReclamosBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
    fun volverinicio(view: View) {
        val intent2 = Intent(this, Home::class.java)
        startActivity(intent2)
    }
}