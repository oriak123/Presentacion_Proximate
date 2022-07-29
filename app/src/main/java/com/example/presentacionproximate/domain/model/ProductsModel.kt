package com.example.presentacionproximate.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ProductsModel(

    val codeStatus: String,
    @SerializedName("data") // esto es para guardar lo que recibimos con el nombre del json en la siguiente variable.
    val _data: String,
    val message: String,
    val status: Boolean
){
    val data: Data
        get() = Gson().fromJson(_data, Data::class.java) // Aqui estamos convirtiendo el string en un objeto de data class "Data"
}