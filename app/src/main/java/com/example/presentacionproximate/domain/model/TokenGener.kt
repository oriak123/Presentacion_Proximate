package com.example.presentacionproximate.domain.model

import com.google.gson.annotations.SerializedName

data class TokenGener(
    // El token que se guarda en  una data class TokenGener para ser enviado (body) al endpoint del api.
    @SerializedName("userToken") val userToken: String

)
