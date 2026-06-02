package com.example.chocolate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EliminarAdapter (private val lista: MutableList<Chocolate>) : RecyclerView.Adapter<EliminarAdapter.ViewHolderClass>(){

    private val selectedIndices = mutableSetOf<Int>()
//OnCreateViewHolder es para crear un nuevo item y que el ViewHolder sea donde inflarlo
    override fun onCreateViewHolder(
        parent: ViewGroup, //tamaño y forma del contenedor
        viewType: Int
    ): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holder_eliminar, parent, false) //le decimos que va a usar nuestro diseño del holder
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val item = lista[position]
        holder.nombre.text = item.nombre
        holder.tipo.text = item.tipo
        holder.peso.text = item.peso

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = selectedIndices.contains(position)

        //Es como el listener de un botón pero aquí verifica si la checkbox se hace click
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val pos = holder.adapterPosition
            if (pos == RecyclerView.NO_ID.toInt()) return@setOnCheckedChangeListener // Es una validación de seguridad para asegurar que el elemento todavía existe y tiene una posición válida antes de hacer nada
            if (isChecked) selectedIndices.add(pos)
            else selectedIndices.remove(pos)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun deleteSelected() {
        for (i in selectedIndices.sortedDescending()) {  //de atrás hacia adelante
            lista.removeAt(i)
        }
        selectedIndices.clear()
        notifyDataSetChanged()
    }

    class ViewHolderClass (view : View) :
        RecyclerView.ViewHolder(view){
        val nombre = view.findViewById<TextView>(R.id.tvNombre)
        val tipo = view.findViewById<TextView>(R.id.tvTipo)
        val peso = view.findViewById<TextView>(R.id.tvPeso)
        val checkBox = view.findViewById<CheckBox>(R.id.cbSelect)
    }
}
