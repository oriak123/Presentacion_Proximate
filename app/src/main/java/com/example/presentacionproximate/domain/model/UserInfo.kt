package com.example.presentacionproximate.domain.model

import com.google.gson.annotations.SerializedName

data class UserInfo(

    // El nombre del usuario y password que se guarda en una data class UserInfo para ser enviado al endpoint del api.
    @SerializedName("user") val user: String?,
    @SerializedName("password") val password: String?,

)
