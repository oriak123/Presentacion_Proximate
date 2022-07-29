package com.example.presentacionproximate.ui.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.example.presentacionproximate.R
import com.example.presentacionproximate.databinding.HolderRecyclerBinding
import com.example.presentacionproximate.domain.model.DataProductModel

class listViewHolder(

    private val binding: HolderRecyclerBinding,
    private val itemClickListener: listAdapter.OnProductClickListener

) : RecyclerView.ViewHolder(binding.root) {

    fun render(itemProducto: DataProductModel) {

        itemView.setOnClickListener(){itemClickListener.onItemClickListener(itemProducto.longDescription, itemProducto.image, itemProducto.title)}
        binding.Titulo.text = itemProducto.title.toUpperCase()
        binding.imageProduct.loadUrl(itemProducto.image)
        Glide.with(binding.imageProduct).load(itemProducto.image).centerCrop().error(R.drawable.cargando2).into(binding.imageProduct)

    }

    fun ImageView.loadUrl(url: String) {

        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(200)
            .data(url).error(R.drawable.cargando2)
            .target(this)
            .build()

        imageLoader.enqueue(request)

    }
}