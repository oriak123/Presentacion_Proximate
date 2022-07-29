package com.example.presentacionproximate.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.presentacionproximate.Home
import com.example.presentacionproximate.R
import com.example.presentacionproximate.databinding.ActivityLoginBinding
import com.example.presentacionproximate.data.RestApiService
import com.example.presentacionproximate.domain.model.UserInfo
import com.example.presentacionproximate.ui.utils.GlobalDatesApplication.Companion.prefs

private lateinit var binding: ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContraseña: EditText
    private lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        etUsuario = findViewById(R.id.etUsuario)
        etContraseña = findViewById(R.id.etContraseña)
        progressbar = findViewById(R.id.progressBar)

        binding.btnLogin.setOnClickListener { iniciarSesion() }


        autocompletarUsuario()
        recordarUsuario()
    }

    private fun recordarUsuario() {

        // Comprobando que el usuario, contraseña y token no esten vacios para ingresar automaticamente al Home.
        if ((prefs.getUser().isNotEmpty() && prefs.getPassw().isNotEmpty()) && prefs.getToken()
                .isNotEmpty()
        ) {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun autocompletarUsuario() {

        val usuario = prefs.getUser()
        val contraseña = prefs.getPassw()

        // Comprobando que usuario y contraseña no esten vacios para autocompletar los campos a la hora de cerrar sesion.
        if (usuario.isNotEmpty() && contraseña.isNotEmpty()) {

            binding.etUsuario.setText(usuario)
            binding.etContraseA.setText(contraseña)
        }
    }


    fun iniciarSesion() {

        progressbar.visibility = View.VISIBLE

        // Convirtiendo datos ingresados a Strings.
        val user: String = etUsuario.text.toString()
        val password: String = etContraseña.text.toString()

        // Si user y password no estan vacios
        if (user.isNotEmpty() && password.isNotEmpty()) {

            val apiService = RestApiService()
            val Usuario = UserInfo(
                user = user,
                password = password
            )

            apiService.getLogin(Usuario) { usuarioRespuesta ->

                Log.d("usuresp", usuarioRespuesta.toString())

                if (usuarioRespuesta != null && usuarioRespuesta.status != false) {




                   // Log.d("hola", binding.checkbox.isChecked.toString())

                    prefs.saveRemember(binding.checkbox.isChecked)
                    prefs.saveUser(binding.etUsuario.text.toString())
                    prefs.savePassw(binding.etContraseA.text.toString())

                    usuarioRespuesta!!.dataUser!!.name?.let { action2(it) }
                    usuarioRespuesta!!.dataUser!!.userToken?.let { action(it) }
                   // Log.d("100", usuarioRespuesta.toString())
                } else {

                    progressbar.visibility = View.GONE
                    Toast.makeText(
                        this, "Por favor ingrese los datos correctos", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {

            Toast.makeText(
                this, "Por favor ingrese los datos correctos", Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun action(tokenUsuario: String) {

        prefs.saveToken(tokenUsuario)
        //Log.d("Otracosa", prefs.getToken())
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    private fun action2(saludo: String) {
        prefs.saveGreeting(saludo)
       // Log.d("OtroName", prefs.getGreeting())
    }
}