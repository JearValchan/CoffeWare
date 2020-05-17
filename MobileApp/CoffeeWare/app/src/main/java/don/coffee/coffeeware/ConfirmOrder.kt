package don.coffee.coffeeware

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.activity_confirm_order.precioTotal
import kotlinx.android.synthetic.main.producto_orden.*
import kotlinx.android.synthetic.main.producto_orden.view.*
import org.json.JSONObject

class ConfirmOrder : AppCompatActivity() {

    var productosPersonalizados = ArrayList<ProductoPersonalizado>()

    var orden: Orden = Orden(10000,"no cliente","pendiente",0.0, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)

        productosPersonalizados = SessionData.ordenActual

        var adaptador = AdapterConfirmar(this, productosPersonalizados)
        list_orden.adapter = adaptador

        var intentMenu = Intent(this, MainActivity::class.java)

        precioTotal.text = obtenerPrecioFinal().toString()

        menuBtn.setOnClickListener{
            startActivity(intentMenu)
        }

        var intentEditar = Intent(this, EditOrder::class.java)
        btn_editarorden.setOnClickListener(){
            startActivity(intentEditar)
        }

        cancelBtn.setOnClickListener{
            productosPersonalizados.clear()
            adaptador!!.notifyDataSetChanged()
            startActivity(intentMenu)
            Toast.makeText(this, "Orden Eliminada", Toast.LENGTH_LONG).show()
        }

        list_orden.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            if (list_ingredients.visibility == VISIBLE) list_ingredients.visibility = GONE else list_ingredients.visibility = VISIBLE
        }

        val intent = Intent(this, MainActivity::class.java)
        btn_enviarorden.setOnClickListener{
            if(llenarDatos()){
                enviarOrden()
                SessionData.ordenActual.clear()
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "Indicar el nombre de el consumidor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun llenarDatos(): Boolean {

        if(edit_consumidor.text.toString().equals("")){
            return false
        }else{
        orden.cliente = edit_consumidor.text.toString()
        orden.ESTADO = "En cola"
        SessionData.ordenes.add(orden)
        val rnds = (0..100000).random()
        orden.ID = rnds
        orden.preciofinal = obtenerPrecioFinal()
        orden.productos = SessionData.ordenActual
        }
        return true
    }

    fun obtenerPrecioFinal(): Double{
        var total: Double = 0.0

        for(x in SessionData.ordenActual){
            total += x.preciobase+x.precioExtra
        }
        return total
    }

    fun enviarOrden(){
        Toast.makeText(this,"${orden.ID} ${orden.preciofinal} ${orden.ESTADO} ${orden.cliente}",Toast.LENGTH_SHORT).show()

        var url: String = "http://192.168.0.13/coffeeware/wsJSONRegistroOrdenes.php?ID="+orden.ID.toString()+"&cliente="+orden.cliente+"&ESTADO="+orden.ESTADO+"&preciofinal="+orden.preciofinal
        val jsonobject= JsonObjectRequest(
            Request.Method.POST,url,null,
            Response.Listener<JSONObject?> {
                Toast.makeText(applicationContext, "Orden enviada", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            }
        )
        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonobject)

    }

    private class AdapterConfirmar:BaseAdapter {

        var context: Context? = null
        var productosPersonalizado = ArrayList<ProductoPersonalizado>()

        constructor(context: Context, productosPersonalizado: ArrayList<ProductoPersonalizado>) {
            this.context = context
            this.productosPersonalizado = productosPersonalizado
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var productoPersonalizado = productosPersonalizado[position]
            var inflator = LayoutInflater.from(context)
            var vista = inflator.inflate(R.layout.producto_orden, null)

            vista.product_name.setText(productoPersonalizado.nombrePersonalizado)
            vista.precio.setText("$ "+(productoPersonalizado.precioBasePersonalizado+productoPersonalizado.precioExtra))

            return vista
        }

        override fun getItem(position: Int): Any {
            return productosPersonalizado[position]
        }

        override fun getItemId(position: Int): Long {
            return 1
        }

        override fun getCount(): Int {
            return productosPersonalizado.size
        }

    }

}
