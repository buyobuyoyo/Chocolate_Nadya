package com.example.chocolate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//El adaptador se llama eliminar adapter
//Recibe una lista de chocolates como parametro
//RecyclerView.Adapter<EliminarAdapter.ViewHolderClass> hereda y puede usar los metodos
class EliminarAdapter (private val lista: MutableList<Chocolate>) : RecyclerView.Adapter<EliminarAdapter.ViewHolderClass>(){

    private val selectedIndices = mutableSetOf<Int>()


// OnCreateViewHolder es para crear un nuevo item y que el ViewHolder sea donde inflarlo
// Osea que infla nuestro XML item_holder_eliminar para cada tarjeta y asi poder utilizarlo
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
        //El chocolate en esta posición está marcado.

        //Es como el listener de un botón pero aquí verifica si la checkbox se hace click
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            // Si isChecked es true, el usuario la acaba de marcar.
            val pos = holder.bindingAdapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnCheckedChangeListener // Es una validación de seguridad para asegurar que el elemento todavía existe y tiene una posición válida antes de hacer nada
            if (isChecked) selectedIndices.add(pos) // Guarda el número de posición de ese chocolate en selected indices
            else selectedIndices.remove(pos) // Le quitaste la palomita así que ya no se va a borrar
        }
    }

    override fun getItemCount(): Int = lista.size

    fun deleteSelected() {
        for (i in selectedIndices.sortedDescending()) {  //de atrás hacia adelante
            lista.removeAt(i) // Esto quita el chocolate de la mutable list
        }
        selectedIndices.clear() //Le dice a la variable que almacenaba los indices del choco eliminado que los olvide
        notifyDataSetChanged() // Le dice a la vista que la lista cambió
    }

    class ViewHolderClass (view : View) :
        RecyclerView.ViewHolder(view){
        val nombre = view.findViewById<TextView>(R.id.tvNombre)
        val tipo = view.findViewById<TextView>(R.id.tvTipo)
        val peso = view.findViewById<TextView>(R.id.tvPeso)
        val checkBox = view.findViewById<CheckBox>(R.id.cbSelect)
    }
}
