package com.example.chocolate
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import com.example.chocolate.choco.listaChocolates



class MainActivity : AppCompatActivity() {


    lateinit var spinnerPresentacion: Spinner
    lateinit var spinnerTipoCacao: Spinner
    lateinit var spinnerPerfilSabor: Spinner
    lateinit var spinnerTipo: Spinner
    lateinit var spinnerPeso: Spinner
    lateinit var etNombre: EditText
    lateinit var etMarca: EditText
    lateinit var etPaisOrigen: EditText
    lateinit var etTelefono: EditText
    lateinit var etPorcentajeCacao: EditText
    lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinnerPresentacion = findViewById(R.id.spinnerPresentacion)
        spinnerTipoCacao = findViewById(R.id.spinnerTipoCacao)
        spinnerPerfilSabor = findViewById(R.id.spinnerPerfilSabor)
        spinnerTipo = findViewById(R.id.spinnerTipo)
        spinnerPeso = findViewById(R.id.spinnerPeso)
        etNombre = findViewById(R.id.etNombre)
        etMarca = findViewById(R.id.etMarca)
        etPaisOrigen = findViewById(R.id.etPaisOrigen)
        etTelefono = findViewById(R.id.etTelefono)
        etPorcentajeCacao = findViewById(R.id.etPorcentajeCacao)
        btnGuardar = findViewById(R.id.btnGuardar)

        val toolbar:Toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val adapterPresentacion = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("Barra", "Trufa", "Bombón", "Polvo"))

        adapterPresentacion.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        spinnerPresentacion.adapter = adapterPresentacion

        // Tipo Cacao
        val adapterTipoCacao = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("Criollo", "Forastero", "Trinitario"))
        adapterTipoCacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipoCacao.adapter = adapterTipoCacao

// Perfil Sabor
        val adapterPerfil = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("Amargo", "Dulce", "Afrutado", "Especiado"))
        adapterPerfil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPerfilSabor.adapter = adapterPerfil


        val adapterTipo = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("Amargo", "Con leche", "Blanco", "Artesanal", "SemiAmargo", "Gourmet", "Ruby"))
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipo.adapter = adapterTipo


        val adapterPeso = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            listOf("100g", "90g", "250g"))
        adapterPeso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPeso.adapter = adapterPeso

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val marca = etMarca.text.toString()
            val paisOrigen = etPaisOrigen.text.toString()
            val telefono = etTelefono.text.toString()
            val porcentaje = etPorcentajeCacao.text.toString()

            when {
                nombre.isEmpty() -> Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                marca.isEmpty() -> Toast.makeText(this, "La marca es obligatoria", Toast.LENGTH_SHORT).show()
                paisOrigen.isEmpty() -> Toast.makeText(this, "El país de origen es obligatorio", Toast.LENGTH_SHORT).show()
                telefono.isEmpty() -> Toast.makeText(this, "El teléfono es obligatorio", Toast.LENGTH_SHORT).show()
                !telefono.all { it.isDigit() } -> Toast.makeText(this, "El teléfono solo debe contener números", Toast.LENGTH_SHORT).show()
                telefono.length != 10 -> Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
                porcentaje.isEmpty() -> Toast.makeText(this, "El % de cacao es obligatorio", Toast.LENGTH_SHORT).show()
                else -> {
                    val chocolate = Chocolate(
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
                    choco.listaChocolates.add(chocolate)
                    Toast.makeText(this, "Chocolate guardado!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)


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
            Toast.makeText(this, "Ya estas en la opcion", Toast.LENGTH_LONG).show()
        }

        if (item.itemId == R.id.opc2) {
            if (choco.listaChocolates.size <= 0) {
                Toast.makeText(this, "No hay chocolates registrados", Toast.LENGTH_SHORT).show()
            } else {
                val cambio = Intent(this, Ver::class.java)
                startActivity(cambio)
            }
        }

        if (item.itemId == R.id.opc3) {
            if (choco.listaChocolates.size <= 0) {
                Toast.makeText(this, "No hay chocolates registrados", Toast.LENGTH_SHORT).show()
            } else {
                val cambio = Intent(this, VistaLista::class.java)
                startActivity(cambio)
            }
        }

        if (item.itemId == R.id.opc4) {
            if (choco.listaChocolates.size <= 0) {
                Toast.makeText(this, "No hay chocolates registrados", Toast.LENGTH_SHORT).show()
            } else {
                val cambio = Intent(this, eliminarchocolate::class.java)
                startActivity(cambio)
            }
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