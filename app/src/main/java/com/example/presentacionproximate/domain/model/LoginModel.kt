package com.example.presentacionproximate.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LoginModel(
    val codeStatus: String,
    @SerializedName("data")
    val _data: String?="",
    val message: String,
    val status: Boolean,

    ){
    val dataUser: DataUser?
        get() = Gson().fromJson(_data, DataUser::class.java)?:   DataUser()
}