package com.example.chocolate

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class eliminarchocolate : AppCompatActivity() {

    lateinit var recy: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eliminarchocolate)

        val toolbar : Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recy=findViewById<RecyclerView>(R.id.rv)
        recy.layoutManager= LinearLayoutManager(this)

        val adapter = EliminarAdapter(choco.listaChocolates)
        recy.adapter=adapter
        adapter.notifyDataSetChanged() //Para que haga las actualizaciones al adaptador, como si le dijera "Hey te actualizaste"

        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        btnEliminar.setOnClickListener {
            adapter.deleteSelected()
            Toast.makeText(this, "Chocolates eliminados", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)


        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val rol = prefs.getString("rol", "")

        if (rol == "trabajador") {

            menu?.findItem(R.id.opc1)?.isVisible = false  // Registro
            menu?.findItem(R.id.opc3)?.isVisible = false  // Cambiar
            menu?.findItem(R.id.opc4)?.isVisible = false  // Eliminar
            menu?.findItem(R.id.opc_eliminar)?.isVisible = false // Bote de basura
        }


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.opc1) {
            val cambio= Intent(this, MainActivity::class.java)
            startActivity(cambio)
        }

        if (item.itemId == R.id.opc2) {
            val cambio = Intent(this, Ver::class.java)
            startActivity(cambio)
        }

        if (item.itemId == R.id.opc3) {
            val cambio= Intent(this, VistaLista::class.java)
            startActivity(cambio)
        }

        if (item.itemId == R.id.opc4) {
//            Toast.makeText(this, "Ya estas en la opcion", Toast.LENGTH_LONG).show()
        }

        if (item.itemId == R.id.opc5) {
            val cambio = Intent(this, creador::class.java)
            startActivity(cambio)
        }

        if (item.itemId == R.id.opc6) {
            val cambio = Intent(this, contacto::class.java)
            startActivity(cambio)
        }

        if (item.itemId == R.id.opc_cerrar_sesion) {
            val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
            prefs.edit().clear().apply() // 👈 limpia el SharedPreferences
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        if (item.itemId == R.id.opc_eliminar) {
            if (choco.listaChocolates.size <= 0) {
                Toast.makeText(this, "No hay chocolates registrados", Toast.LENGTH_SHORT).show()
            } else {
                val cambio = Intent(this, eliminarchocolate::class.java)
                startActivity(cambio)
            }
        }

        return super.onOptionsItemSelected(item)

    }


}