package com.example.testapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.example.testapplication.ListaProductosActivity
import kotlinx.android.synthetic.main.activity_lista_productos2.*
import kotlinx.android.synthetic.main.producto_item.view.*

class ListaProductosActivity : AppCompatActivity() {

    var productos = ArrayList<Producto>()
    lateinit var request: RequestQueue
    lateinit var jsonObjectRequest:JsonObjectRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos2)
        val bundle = intent.extras

        if (bundle != null) {
            productos = bundle.getSerializable("list") as ArrayList<Producto>
        }

        var adaptador = AdapterProducto(this, productos)
        listaProductos.adapter = adaptador
    }

    private class AdapterProducto: BaseAdapter {
        var contexto:Context? = null
        var productos = ArrayList<Producto>()

        constructor(contexto:Context, productos: ArrayList<Producto>){
            this.contexto = contexto
            this.productos = productos
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var produ = productos[position]
            var inflator = LayoutInflater.from(contexto)
            var vista = inflator.inflate(R.layout.producto_item, null)
            vista.idProducto.setText(produ.nombre)
            vista.tipoProducto.setText(produ.producto_type)
            vista.nombreProducto.setText(produ.nombre)
            vista.precioBaseProducto.setText(produ.preciobase.toString())
            vista.idCategoria.setText(produ.id_categoria)

            return vista
        }

        override fun getItem(position: Int): Any {
            return productos[position]
        }

        override fun getItemId(position: Int): Long {
            return 1
        }

        override fun getCount(): Int {
            return productos.size
        }

    }
}