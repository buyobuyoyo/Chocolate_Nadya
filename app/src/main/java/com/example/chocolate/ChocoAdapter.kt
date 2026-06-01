    package com.example.chocolate

    import android.content.Intent
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView

    class ChocoAdapter(private val lista: List<Chocolate>) :
        RecyclerView.Adapter<ChocoAdapter.ViewHolderClass>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolderClass {
           val view= LayoutInflater.from(parent.context).inflate(R.layout.item_holder,parent,false)
            return ViewHolderClass(view)
        }

        override fun onBindViewHolder(
            holder: ViewHolderClass,
            position: Int){
            val item=lista[position]

            holder.nombre.text=item.nombre
            holder.marca.text=item.marca
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, tarjeta::class.java)
                intent.putExtra("pos", position)
                holder.itemView.context.startActivity(intent)
            }

        }


        override fun getItemCount(): Int {
            return lista.size
        }

        class ViewHolderClass(view: View) : RecyclerView.ViewHolder(view) {
            val nombre = view.findViewById<TextView>(R.id.txtNombre)
            val marca = view.findViewById<TextView>(R.id.txtMarca)
        }
    }