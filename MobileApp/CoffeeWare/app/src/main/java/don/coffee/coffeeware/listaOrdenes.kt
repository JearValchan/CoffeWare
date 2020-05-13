package don.coffee.coffeeware

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_lista_ordenes.*
import kotlinx.android.synthetic.main.activity_lista_ordenes.view.*
import kotlinx.android.synthetic.main.viewlistaordenes.*
import kotlinx.android.synthetic.main.viewlistaordenes.view.*
import org.json.JSONObject
import don.coffee.coffeeware.Orden as Orden

class listaOrdenes : AppCompatActivity() {

    var ordenes = ArrayList<Orden>()
    var adaptador: adaptadorOrdenes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_ordenes)


        adaptador = adaptadorOrdenes(this,ordenes)
        listview_ordenes.adapter = adaptador

        cargarOrdenes("http://192.168.1.74:80/coffeeware/wsJSONConsultarListaOrdenes.php")

    }


    fun editarOrden(){

    }

    fun eliminarOrden(view: View){


        var orden =  adaptador!!.getItem(view.verticalScrollbarPosition) as Orden
        var id = orden.ID
        var urlEliminar = "http://192.168.1.74:80/coffeeware/wsJSONEliminarOrden.php?ID=" + id
        var estado:String = orden.ESTADO


            val eliminar = JsonObjectRequest(
                Request.Method.POST,
                urlEliminar,
                null,
                Response.Listener<JSONObject?> {
                    Toast.makeText(this, "Orden Eliminada", Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { error ->
                    ordenes.remove(orden)
                    adaptador!!.notifyDataSetChanged()
                    Toast.makeText(this, "Orden Eliminada", Toast.LENGTH_LONG).show()

                })
            var requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(eliminar)



    }
    fun cargarOrdenes(URL:String){

        val jsonobject = JsonObjectRequest(Request.Method.GET,URL,null, Response.Listener { response ->

            var JSON = response.getJSONArray("orden")
            val gson = Gson()
            for(i in 0..(JSON.length()-1)){
                var lecturaOrdenes = JSON[i].toString()

                var ordenTemp = gson.fromJson(lecturaOrdenes,Orden::class.java)
                ordenes.add(ordenTemp)
                adaptador!!.notifyDataSetChanged()

            }

        },Response.ErrorListener { error ->
            Toast.makeText(this,error.toString(), Toast.LENGTH_LONG).show()
        })
        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonobject)
    }

    class adaptadorOrdenes :BaseAdapter{
        var ordenes =ArrayList<Orden>()
        var contexto: Context? = null

        constructor(contexto:Context, ordenes:ArrayList<Orden>){
            this.ordenes = ordenes
            this.contexto = contexto
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
           var orden = ordenes[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.viewlistaordenes,null)

            vista.textview_nombreCliente.setText(orden.cliente)
            vista.textview_precioFinal.setText(orden.preciofinal.toString())
            vista.textview_estado.setText(orden.ESTADO.toString())
            vista.textview_id.setText(orden.ID.toString())


            return vista
        }

        override fun getItem(position: Int): Any {
            return ordenes[position]
        }

        override fun getItemId(position: Int): Long {
           return position.toLong()
        }

        override fun getCount(): Int {
           return ordenes.size
        }

    }



}
