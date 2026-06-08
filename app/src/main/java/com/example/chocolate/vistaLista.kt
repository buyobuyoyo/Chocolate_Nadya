package com.example.chocolate

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class VistaLista : AppCompatActivity() {

    lateinit var etNombre: EditText
    lateinit var etMarca: EditText
    lateinit var etPaisOrigen: EditText
    lateinit var etTelefono: EditText
    lateinit var etPorcentajeCacao: EditText
    lateinit var spinnerPresentacion: Spinner
    lateinit var spinnerTipoCacao: Spinner
    lateinit var spinnerPerfilSabor: Spinner
    lateinit var spinnerTipo: Spinner
    lateinit var spinnerPeso: Spinner
    lateinit var btnAnterior: Button
    lateinit var btnGuardar: Button
    lateinit var btnSiguiente: Button

    var posicionActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_lista)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        etNombre = findViewById(R.id.etNombre)
        etMarca = findViewById(R.id.etMarca)
        etPaisOrigen = findViewById(R.id.etPaisOrigen)
        etTelefono = findViewById(R.id.etTelefono)
        etPorcentajeCacao = findViewById(R.id.etPorcentajeCacao)
        spinnerPresentacion = findViewById(R.id.spinnerPresentacion)
        spinnerTipoCacao = findViewById(R.id.spinnerTipoCacao)
        spinnerPerfilSabor = findViewById(R.id.spinnerPerfilSabor)
        spinnerTipo = findViewById(R.id.spinnerTipo)
        spinnerPeso = findViewById(R.id.spinnerPeso)
        btnAnterior = findViewById(R.id.btnAnterior)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnSiguiente = findViewById(R.id.btnSiguiente)

        spinnerPresentacion.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("Barra", "Trufa", "Bombón", "Polvo"))

        spinnerTipoCacao.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("Criollo", "Forastero", "Trinitario"))

        spinnerPerfilSabor.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("Amargo", "Dulce", "Afrutado", "Especiado"))

        spinnerTipo.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("Amargo", "Con leche", "Blanco", "Artesanal", "SemiAmargo", "Gourmet", "Ruby"))

        spinnerPeso.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("100g", "90g", "250g"))

        mostrarRegistro(posicionActual)

        btnSiguiente.setOnClickListener {
            posicionActual = (posicionActual + 1) % choco.listaChocolates.size //cambia de chocolate con una operación matemática, por ejemplo imagina que tienes 3 registros y
            // la posicion actual es 3, ésta se divide entre listaChocolates.size y da 0 así que así vuelve al comienzo.
            mostrarRegistro(posicionActual)
        }

        btnAnterior.setOnClickListener {
            posicionActual = if (posicionActual == 0)
                choco.listaChocolates.size - 1
            else
                posicionActual - 1
            mostrarRegistro(posicionActual)
        }

        btnGuardar.setOnClickListener {
            guardarCambios()
        }
    }
    private fun mostrarRegistro(pos: Int) {
        val item = choco.listaChocolates[pos]
        etNombre.setText(item.nombre)
        etMarca.setText(item.marca)
        etPaisOrigen.setText(item.paisOrigen)
        etTelefono.setText(item.telefonoContacto)
        etPorcentajeCacao.setText(item.porcentajeCacao)
        val presentaciones = listOf("Barra", "Trufa", "Bombón", "Polvo")
        spinnerPresentacion.setSelection(presentaciones.indexOf(item.presentacion))

        val tiposCacao = listOf("Criollo", "Forastero", "Trinitario")
        spinnerTipoCacao.setSelection(tiposCacao.indexOf(item.tipoCacao))

        val perfilesSabor = listOf("Amargo", "Dulce", "Afrutado", "Especiado")
        spinnerPerfilSabor.setSelection(perfilesSabor.indexOf(item.perfilSabor))

        val tipos = listOf("Amargo", "Con leche", "Blanco", "Artesanal", "SemiAmargo", "Gourmet", "Ruby")
        spinnerTipo.setSelection(tipos.indexOf(item.tipo))

        val pesos = listOf("100g", "90g", "250g")
        spinnerPeso.setSelection(pesos.indexOf(item.peso))
    }

    private fun guardarCambios() {
        val nombre = etNombre.text.toString()
        val marca = etMarca.text.toString()
        val paisOrigen = etPaisOrigen.text.toString()
        val telefono = etTelefono.text.toString()
        val porcentaje = etPorcentajeCacao.text.toString()

        when {
            nombre.isEmpty() -> { Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show(); return }
            marca.isEmpty() -> { Toast.makeText(this, "La marca es obligatoria", Toast.LENGTH_SHORT).show(); return }
            paisOrigen.isEmpty() -> { Toast.makeText(this, "El país de origen es obligatorio", Toast.LENGTH_SHORT).show(); return }
            telefono.isEmpty() -> { Toast.makeText(this, "El teléfono es obligatorio", Toast.LENGTH_SHORT).show(); return }
            !telefono.all { it.isDigit() } -> { Toast.makeText(this, "El teléfono solo debe contener números", Toast.LENGTH_SHORT).show(); return }
            telefono.length != 10 -> { Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show(); return }
            porcentaje.isEmpty() -> { Toast.makeText(this, "El % de cacao es obligatorio", Toast.LENGTH_SHORT).show(); return }
        }


        val chocolateEditado = Chocolate(
            nombre = nombre,
            marca = marca,
            paisOrigen = paisOrigen,
            telefonoContacto = telefono,
            porcentajeCacao = porcentaje,
            presentacion = spinnerPresentacion.selectedItem.toString(),
            tipoCacao = spinnerTipoCacao.selectedItem.toString(),
            perfilSabor = spinnerPerfilSabor.selectedItem.toString(),
            tipo = spinnerTipo.selectedItem.toString(),
            peso = spinnerPeso.selectedItem.toString()
        )
        choco.listaChocolates[posicionActual] = chocolateEditado //Aquí se guarda el nuevo chocolate
        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
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
            val cambio = Intent(this, MainActivity::class.java)
            startActivity(cambio)
        }
        if (item.itemId == R.id.opc2) {
            val cambio = Intent(this, Ver::class.java)
            startActivity(cambio)
        }
        if (item.itemId == R.id.opc3) {
//            Toast.makeText(this, "Ya estás en esta opción", Toast.LENGTH_SHORT).show()
        }

        if (item.itemId == R.id.opc4) {
            if (choco.listaChocolates.size <= 0) {
                Toast.makeText(this, "No hay chocolates registrados", Toast.LENGTH_SHORT).show()
            } else {
                val cambio = Intent(this, eliminarchocolate::class.java)
                startActivity(cambio)
            }
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
            prefs.edit().clear().apply() // limpia el SharedPreferences
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