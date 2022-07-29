package com.example.presentacionproximate.domain.model

import com.google.gson.annotations.SerializedName

data class TokenGener(

    @SerializedName("userToken") val userToken: String

)
