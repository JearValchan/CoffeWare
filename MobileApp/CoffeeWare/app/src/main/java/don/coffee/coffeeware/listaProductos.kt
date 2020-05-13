package don.coffee.coffeeware

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_lista_productos.*
import kotlinx.android.synthetic.main.activity_personalizar_producto.view.*
import kotlinx.android.synthetic.main.viewlistaordenes.view.*
import kotlinx.android.synthetic.main.viewlistaproductos.view.*

class listaProductos : AppCompatActivity() {

    var adaptador: adaptadorProducto? = null
    var productos = ArrayList<Producto>()
    var urlCargarProductos ="http://192.168.1.74:80/coffeeware/wsJSONConsultarListaProductos.php"



    //Auxiliares
    var ing = IngredienteBase("Pastel")
    var porcionIngre = PorcionIngredienteBase(2, ing)
    var porciones = ArrayList<PorcionIngredienteBase>()
    var extra = IngredienteExtra("Leche", 5.0)
    var extras = ArrayList<IngredienteExtra>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)

        adaptador = adaptadorProducto(this,productos)
        listaProductos.adapter = adaptador

        cargarAuxiliares()
        cargarProductos()

        btn_agregarProducto.setOnClickListener {
            val intent = Intent(this,agregarProducto::class.java)
            startActivity(intent)

        }
    }

    fun cargarAuxiliares() {
        porciones.add(porcionIngre)
        porciones.add(porcionIngre)
        porciones.add(porcionIngre)

        extras.add(extra)
        extras.add(extra)
        extras.add(extra)

        println("Ya se crearon todos los extra")

    }

    fun cargarProductos(){
        val jsonobject = JsonObjectRequest(Request.Method.GET,urlCargarProductos,null,Response.Listener { response ->

            var JSON = response.getJSONArray("producto")
            val gson = Gson()
            var tam = JSON.length()-1

            for( i in 0..tam) {
                var productoJson = JSON[i].toString()

                var ultimo:Int = (productoJson.lastIndex)-2
                var categoria:Categoria =Categoria("Alimentos",0)

                when (productoJson[ultimo].toInt()){
                    0 ->  categoria = Categoria("Alimentos",0)
                    1 ->  categoria = Categoria("Bebidas",1)
                    2 ->  categoria = Categoria("Postres",2)
                }
                val productoTemp:Producto = gson.fromJson(productoJson,Producto::class.java)
                productoTemp.categoria = categoria
                productoTemp.image = R.drawable.image_icon
                productoTemp.descripcion="Descripcion"
                productoTemp.ingredientesBase = porciones
                productoTemp.ingredientesExtra=extras
                productos.add(productoTemp)
                adaptador!!.notifyDataSetChanged()
            }


        },Response.ErrorListener { error ->
            Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()
        })

        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonobject)
    }

    fun editarProducto(view:View){


        var productoMandar = adaptador!!.getItem(view.verticalScrollbarPosition) as Producto

        val intent = Intent(this,agregarProducto::class.java)
        intent.putExtra("producto",productoMandar)
        startActivity(intent)

    }

    fun eliminarProducto(view: View){

        var produc = adaptador!!.getItem(view.verticalScrollbarPosition) as Producto
        var id =produc.ID

        var url:String="http://192.168.1.74:80/coffeeware/wsJSONEliminarProducto.php?ID=" + id


        var stringRequest = StringRequest(url, Response.Listener<String> { response ->
            if (response.trim().equals("elimina", true)){
                Toast.makeText(applicationContext, "ELIMINADO CON EXITO", Toast.LENGTH_SHORT).show()
                productos.remove(produc)
                adaptador!!.notifyDataSetChanged()
            }else{
                if (response.trim().equals("noExiste", true)){
                    Toast.makeText(applicationContext, "No se encuentra a la persona", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "No se ha eliminado", Toast.LENGTH_SHORT).show()
                }
            }
        },
            Response.ErrorListener {
                Toast.makeText(applicationContext, toString(), Toast.LENGTH_SHORT).show()
            }
        )
        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
    }


    class adaptadorProducto:BaseAdapter{
        var productos =ArrayList<Producto>()
        var contexto: Context? = null

        constructor(contexto:Context,productos:ArrayList<Producto>){
            this.productos = productos
            this.contexto = contexto
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var producto = productos[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.viewlistaproductos,null)

            vista.textview_nombreproducto.setText(producto.nombre)
            vista.textview_categoria.setText(producto.categoria.nombre)
            vista.textview_Precio.setText(producto.preciobase.toString())
            vista.textview_idproducto.setText(producto.ID.toString())

            return vista
        }

        override fun getItem(position: Int): Any {
            return productos[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
           return productos.size
        }

    }