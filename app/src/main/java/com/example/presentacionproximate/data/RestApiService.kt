package com.example.presentacionproximate.data


import com.example.presentacionproximate.ServiceBuilder
import com.example.presentacionproximate.data.network.RestApi
import com.example.presentacionproximate.domain.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    fun getLogin(userData: UserInfo, onResult: (LoginModel?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getLogin(userData).enqueue(
            object : Callback<LoginModel> {
                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                    onResult(response.body()!!)
                }
            }
        )
    }

    fun getProducts(userData: TokenGener, onResult: (ProductsModel?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getProducts(userData).enqueue(
            object : Callback<ProductsModel> {
                override fun onFailure(call: Call<ProductsModel>, t: Throwable) {
                    onResult(null) //siletclass (respuestsa)
                }

                override fun onResponse(call: Call<ProductsModel>, response: Response<ProductsModel>) {
                    onResult(response.body()!!)
                }
            }
        )
    }
}