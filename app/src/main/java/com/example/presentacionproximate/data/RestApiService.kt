package com.example.presentacionproximate.data


import com.example.presentacionproximate.data.network.RestApi
import com.example.presentacionproximate.domain.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    // Con el metodo getLogin se manda a llamar a Retrofit y se utiliza el metodo getLogin para obtener el usuario y el token.
    // Recibe un objeto de tipo usuario ya que el endpoint asi lo requiere ademas recibe una funcion lambda (onResult)
    fun getLogin(userData: UserInfo, onResult: (LoginModel?) -> Unit) {
        // Se crea variable retrofit, se manda a llamar a su metodo buildService y este contendra la interface con los endpoint de nuestra api.
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        // Con el objeto retrofit se manda a traer el metodo getLogin o endpoint el cual recibe como parametro userData
        // (enqueue es un metodo de clase call que nos guarda lo que retrofit nos mando en el endpoint.
        retrofit.getLogin(userData).enqueue(

            //call requiere de sobreescribir los metodos onFailure y onResponse.
            object : Callback<LoginModel> {
                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                    onResult(null)
                }
                // Cuando funciona manda un objeto onResult  con el cuerpo de nuestro endpoint.
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