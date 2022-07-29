package com.example.presentacionproximate

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


    object ServiceBuilder {
        private val client = OkHttpClient.Builder().build()

        //  instanciar llamo a clase, y despues metodo
        val retrofit = Retrofit.Builder()
            .baseUrl("https://serveless.proximateapps-services.com.mx")
            .addConverterFactory(GsonConverterFactory.create()) // convertir el Json en un objeto porque es mejor trabajarlo asi para tener mejor acceso  // .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()

        fun <T> buildService(service: Class<T>): T {
            return retrofit.create(service) // retrofit por defecto nos pide interfaz en el metodo create, y esa interfaz es Apiservice
        }
    }
