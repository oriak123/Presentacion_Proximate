package com.example.presentacionproximate.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


    object ServiceBuilder {

        // Se crea el cliente
        private val client = OkHttpClient.Builder().build()

        //  Instanciamos llamando la clase Retrofit, y despues se llama el metodo para su construccion y se construye.
        val retrofit = Retrofit.Builder()
            .baseUrl("https://serveless.proximateapps-services.com.mx")
            .addConverterFactory(GsonConverterFactory.create()) // Metodo que recibe el tipo de converscion que retrofit ocupara.
            .client(client)
            .build()

        fun <T> buildService(service: Class<T>): T { // buildService recibe una clase llamada service (Interfaz RestApi)
            return retrofit.create(service) // retrofit por defecto nos pide interfaz en el metodo create, y esa interfaz es RestApi
        }
    }
