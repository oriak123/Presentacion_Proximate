package com.example.presentacionproximate.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LoginModel(

    // Retrofit me regresa los siguientes datos en el endpoint del Login.

    val codeStatus: String,
    @SerializedName("data")
    val _data: String?="",
    val message: String,
    val status: Boolean,

    ){

    // Ya que retrofit no pudo convertir la variable data, esta se guarda en la variable _data y a su vez
    // se convierte en un objeto DataUser, y este objeto es guardado en dataUser.
    val dataUser: DataUser?
        get() = Gson().fromJson(_data, DataUser::class.java)?:   DataUser()
}