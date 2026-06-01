package com.example.chocolate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Login : AppCompatActivity() {

    lateinit var etUsuario: EditText
    lateinit var etContrasena: EditText
    lateinit var btnIniciar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // cambia al nombre de tu xml

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        etUsuario = findViewById(R.id.editTextText)
        etContrasena = findViewById(R.id.editTextText2)
        btnIniciar = findViewById(R.id.button)


        btnIniciar.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val contrasena = etContrasena.text.toString()

            when {
                usuario == "admin" && contrasena == "admin123" -> {
                    guardarSesion("admin")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // 👈 cierra el login para no poder regresar
                }
                usuario == "trabajador" && contrasena == "trabajador123" -> {
                    guardarSesion("trabajador")
                    val intent = Intent(this, Ver::class.java)
                    startActivity(intent)
                    finish()
                }
                usuario == "admin" && contrasena != "admin123" -> {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
                usuario == "trabajador" && contrasena != "trabajador123" -> {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun guardarSesion(rol: String) {
        val prefs: SharedPreferences = getSharedPreferences("sesion", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("rol", rol)
        editor.apply()
    }
}

