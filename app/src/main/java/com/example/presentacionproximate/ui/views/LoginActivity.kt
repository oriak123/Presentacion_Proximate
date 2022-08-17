package com.example.presentacionproximate.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.presentacionproximate.Home
import com.example.presentacionproximate.databinding.ActivityLoginBinding
import com.example.presentacionproximate.data.RestApiService
import com.example.presentacionproximate.domain.model.UserInfo
import com.example.presentacionproximate.ui.utils.GlobalDatesApplication.Companion.prefs



class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Comprobando que usuario y contraseña no esten vacios para autocompletar los campos a la hora de iniciar sesion nuevamente.

        if (usuario.isNotEmpty() && contraseña.isNotEmpty()) {

            //Colocando los strings correspondientes en el caso de que no este vacio.
            binding.etUsuario.setText(usuario)
            binding.etContraseA.setText(contraseña)
        }
    }


    fun iniciarSesion() {

        //Llamando a la propiedad visibilidad de progressbar para colocarla visible.
        binding.progressBar.visibility = View.VISIBLE

        // Convirtiendo datos ingresados a Strings.
        val user: String = binding.etUsuario.text.toString()
        val password: String = binding.etContraseA.text.toString()


        if (user.isNotEmpty() && password.isNotEmpty()) {

            //Llamando a la clase RestApiService y la guardo en la variable apiSerice
            val apiService = RestApiService()
            // Se crea objeto de tipo UserInfo y se le pasa las variables user y password.
            val usuario = UserInfo(
                user = user,
                password = password
            )

            // Con la variable apiService se manda a traer el metodo getLogin al cual se le pasa como parametro el Usuario.
            apiService.getLogin(usuario) { usuarioRespuesta ->

                //Log.d("usuresp", usuarioRespuesta.toString())

                // Si la respuesta del metodo getLogin no es nula y el status no es falso, entonces procede.
                if (usuarioRespuesta != null && usuarioRespuesta.status != false) {

                   // Log.d("hola", binding.checkbox.isChecked.toString())

                    // Guardando estados de las variables y strings.
                    prefs.saveRemember(binding.checkbox.isChecked)
                    prefs.saveUser(binding.etUsuario.text.toString())
                    prefs.savePassw(binding.etContraseA.text.toString())

                    // Con la respuesta del metodo getLogin se extrae el name y el userToken del objeto dataUser.
                    usuarioRespuesta!!.dataUser!!.name?.let { prefs.saveGreeting(it)}
                    usuarioRespuesta!!.dataUser!!.userToken?.let { prefs.saveToken(it) }
                    action()
                   // Log.d("100", usuarioRespuesta.toString())
                } else {

                    //Si la respuesta del metodo getLogin  es nula y el status es falso, entonces procede.
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this, "Por favor ingrese los datos correctos", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else if (user.isNotEmpty() || password.isNotEmpty()) {
                // Si user o password es vacio entonces procede.
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    this, "Por favor ingrese los datos correctos", Toast.LENGTH_SHORT
                ).show()
            }
    }


    private fun action() {
        //Log.d("Otracosa", prefs.getToken())
        // Se crea un Intent el cual inicializa la actividad Home y con finish se destruye la actividad Login.
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
}