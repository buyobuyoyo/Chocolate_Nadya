package com.example.chocolate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EliminarAdapter (private val lista: MutableList<Chocolate>) : RecyclerView.Adapter<EliminarAdapter.ViewHolderClass>(){

    private val selectedItems = mutableSetOf<Chocolate>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holder_eliminar, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolderClass,
        position: Int
    ) {
        val item = lista[position]
        holder.nombre.text = item.nombre
        holder.tipo.text = item.tipo
        holder.peso.text = item.peso

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = selectedItems.contains(item)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) selectedItems.add(item)
            else selectedItems.remove(item)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun deleteSelected() {
        // 👇 Elimina por índice de atrás hacia adelante
        val indices = lista.indices.filter { lista[it] in selectedItems }
        for (i in indices.reversed()) {
            lista.removeAt(i)
        }
        selectedItems.clear()
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
