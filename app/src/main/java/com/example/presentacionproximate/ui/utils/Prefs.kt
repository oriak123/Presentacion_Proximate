package com.example.presentacionproximate.ui.utils

import android.content.Context

class Prefs(val context:Context) {

    val SHARED_NAME = "Datos guardados"
    val SHARED_TOKEN_NAME = "token"
    val SHARED_NAME_NAME = "nameSaludo"
    val PASSWORD = "ContrasenaGuardada"
    val USER ="NombreUsuario"
    val GUARDADO = "UsuarioCheckbox"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveGreeting(saludo:String){
        storage.edit().putString(SHARED_NAME_NAME,saludo).apply()
    }

    fun saveToken(aquiToken:String){
        storage.edit().putString(SHARED_TOKEN_NAME,aquiToken).apply()
    }

    fun saveUser(aquiToken:String){
        storage.edit().putString(USER,aquiToken).apply()
    }

    fun savePassw(aquiToken:String){
        storage.edit().putString(PASSWORD,aquiToken).apply()
    }

    fun saveRemember(aquiToken:Boolean){
        storage.edit().putBoolean(GUARDADO,aquiToken).apply()
    }

    fun getToken ():String{
        return storage.getString(SHARED_TOKEN_NAME,"")!!
    }

    fun getGreeting ():String{
        return storage.getString(SHARED_NAME_NAME,"")!!
    }

    fun getUser ():String{
        return storage.getString(USER,"")!!
    }

    fun getPassw ():String{
        return storage.getString(PASSWORD,"")!!
    }

    fun getRemember ():Boolean{
        return storage.getBoolean(GUARDADO,false)
    }
}