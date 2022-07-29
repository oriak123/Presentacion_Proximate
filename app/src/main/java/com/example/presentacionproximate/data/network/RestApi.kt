package com.example.presentacionproximate.data.network

import com.example.presentacionproximate.domain.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface  RestApi {

    @Headers("Content-Type: application/json") // Cabecera porque el api lo requiere.
    @POST("/proximatetools/dev/webadmin/testproximate/login")
    // El endpoint que el api necesita para obtener el usuario, el cual recibe como parametro un objeto de tipo UserInfor que retrofit requiere como un body.
    fun getLogin(@Body userData: UserInfo): Call<LoginModel>


    @Headers("Content-Type: application/json")
    @POST("proximatetools/dev/webadmin/testproximate/getproducts")
    fun getProducts (@Body userData: TokenGener): Call<ProductsModel>
}