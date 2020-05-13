package don.coffee.coffeeware

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_lista_ordenes.*
import kotlinx.android.synthetic.main.activity_manejar_categoria.*
import kotlinx.android.synthetic.main.viewcategoria.view.*

class manejarCategoria : AppCompatActivity() {
    var adaptador:adaptadorCategoria?=null
    var categorias = ArrayList<Categoria>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manejar_categoria)

        adaptador = adaptadorCategoria(this,categorias)
        listview_categorias.adapter = adaptador
        adaptador!!.notifyDataSetChanged()
        cargarCategorias()

        btn_agregarCategoria.setOnClickListener {

            val intent = Intent(this,agregarCategoria::class.java)
            startActivity(intent)
        }

    }

    fun cargarCategorias(){
        var URL = "http://192.168.1.74:80/coffeeware/wsJSONConsultarListaCategorias.php"
        val jsonA = JsonObjectRequest(Request.Method.GET,URL,null, Response.Listener { response ->
            var JSON = response.getJSONArray("categoria")
            val gson = Gson()
            for(i in 0..JSON.length()-1){

                var categoriaJson = JSON[i].toString()
                var categoriaTemp:Categoria = gson.fromJson(categoriaJson,Categoria::class.java)
                categorias.add(categoriaTemp)
                adaptador!!.notifyDataSetChanged()
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(this,error.toString(), Toast.LENGTH_LONG).show()
        })
        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonA)
        adaptador!!.notifyDataSetChanged()
    }

    fun eliminarCategoria(view: View){
        val categoria = adaptador!!.getItem(view.verticalScrollbarPosition) as Categoria

        val url = "http://192.168.1.74:80/coffeeware/wsJSONEliminarCategoria.php?ID="+categoria.id.toString()

        var stringRequest = StringRequest(url, Response.Listener<String> { response ->
            if (response.trim().equals("elimina", true)){
                Toast.makeText(applicationContext, "ELIMINADO CON EXITO", Toast.LENGTH_SHORT).show()
                categorias.remove(categoria)
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

    class adaptadorCategoria:BaseAdapter{

        var categorias = ArrayList<Categoria>()
        var context: Context? = null

        constructor(context:Context,categorias:ArrayList<Categoria>){
            this.context = context
            this.categorias = categorias
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var categoria = categorias[position]
            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.viewcategoria,null)

            vista.textview_nombrecategoria.setText(categoria.nombre)
            vista.textview_categoriaid.setText(categoria.id.toString())
            return vista
        }

        override fun getItem(position: Int): Any {
            return categorias[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
           return categorias.size
        }

    }



}