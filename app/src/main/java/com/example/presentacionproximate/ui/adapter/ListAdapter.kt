package com.example.presentacionproximate.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentacionproximate.databinding.HolderRecyclerBinding
import com.example.presentacionproximate.domain.model.DataProductModel

class listAdapter(
    private val listado: List<DataProductModel>,
    private val itemClickListener: OnProductClickListener
) : RecyclerView.Adapter<listViewHolder>() {

    interface OnProductClickListener {

        fun onItemClickListener(longDescription: String, image: String, title: String)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listViewHolder {

        val binding = HolderRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return listViewHolder(binding,itemClickListener)

    }

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {

        val item = listado[position]
        holder.render(item)

    }

    override fun getItemCount(): Int = listado.size

}