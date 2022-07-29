package com.example.presentacionproximate.ui.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentacionproximate.data.RestApiService
import com.example.presentacionproximate.databinding.ActivityShowProductsBinding
import com.example.presentacionproximate.domain.model.TokenGener
import com.example.presentacionproximate.ui.utils.GlobalDatesApplication.Companion.prefs
import com.example.presentacionproximate.ui.views.ProductDetail
import com.example.presentacionproximate.ui.utils.GlobalDatesApplication

class ShowProducts : AppCompatActivity(), listAdapter.OnProductClickListener {

    private lateinit var binding: ActivityShowProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityShowProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getToken = prefs.getToken()

        val apiService = RestApiService()


        val token = TokenGener(
            userToken = getToken.toString()

        )
        Log.d("aquiiaa", getToken.toString())
        binding.Recycler.layoutManager = LinearLayoutManager(this)
        apiService.getProducts(token) {
            binding.Recycler.adapter =
                it!!.data.products.let { listAdapter(it!!, this) }

            Log.d("aquii", it?.data?.products.toString())
        }

    }

    override fun onItemClickListener(longDescription: String, image: String, title: String) {

        val intent = Intent(this, ProductDetail::class.java)
        intent.putExtra("descripUrl", longDescription)
        intent.putExtra("imageUrl", image)
        intent.putExtra("titulo", title.uppercase())
        startActivity(intent)

    }
}