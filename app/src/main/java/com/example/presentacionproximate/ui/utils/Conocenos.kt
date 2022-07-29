package com.example.presentacionproximate.ui.utils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.presentacionproximate.databinding.ActivityConocenosBinding
import kotlinx.android.synthetic.main.activity_conocenos.*

class Conocenos : AppCompatActivity() {

    lateinit var binding: ActivityConocenosBinding
    private val BASE_URL = "http://www.proximateapps.mobi/#aboutus"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConocenosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webview.webChromeClient = object : WebChromeClient(){

        }

        webview.webViewClient = object : WebViewClient(){

        }

        val settings = webview.settings
        settings.javaScriptEnabled = true

        webview.loadUrl(BASE_URL)
    }
}