package com.example.presentacionproximate.domain.model


data class DataUser(
    val id: Int?=0,
    val lastName: String?="",
    val name: String?="",
    val position: String?="",
    val role: String?="",
    val userToken: String?=""
)

