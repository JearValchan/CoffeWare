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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_agregar_categoria.*
import kotlinx.android.synthetic.main.activity_agregar_categoria.edtId
import kotlinx.android.synthetic.main.activity_agregar_categoria.edtNombre
import kotlinx.android.synthetic.main.activity_agregar_producto.*
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.producto_orden.*
import kotlinx.android.synthetic.main.producto_orden.view.*
import org.json.JSONObject

class ConfirmOrder : AppCompatActivity() {

    var productosPersonalizados = ArrayList<ProductoPersonalizado>()
    var ingredientesBase = ArrayList<IngredienteBase>()
    var ingredientesExtra = ArrayList<IngredienteExtra>()

    var orden: Orden = Orden(10000,"no cliente","pendiente",0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)

        productosPersonalizados = SessionData.ordenActual

        /*if(productosPersonalizados.isNullOrEmpty()){
            Toast.makeText(this,"NULL OR EMPTY",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,productosPersonalizados.size,Toast.LENGTH_SHORT).show()
        }*/

        //var adaptador = AdapterConfirmar(this, productosPersonalizados, ingredientesBase, ingredientesExtra)
        var adaptador = AdapterConfirmar(this, productosPersonalizados)

        list_orden.adapter = adaptador

        var intentMenu = Intent(this, MainActivity::class.java)

        precioTotal.text = obtenerPrecioFinal().toString()

        menuBtn.setOnClickListener{
            startActivity(intentMenu)
        }

        cancelBtn.setOnClickListener{
            productosPersonalizados.clear()
        }

        list_orden.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            if (list_ingredients.visibility == VISIBLE) list_ingredients.visibility = GONE else list_ingredients.visibility = VISIBLE
        }

        btn_enviarorden.setOnClickListener{
            llenarDatos()
            enviarOrden()
        }
    }

    fun llenarDatos(){
        orden.cliente = edit_consumidor.text.toString()
        orden.ESTADO = "Pendiente"
        SessionData.ordenes.add(orden)
        val rnds = (0..100000).random()
        orden.ID = rnds
        orden.preciofinal = obtenerPrecioFinal()
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
                Toast.makeText(applicationContext, "OPERACIÓN EXITOSA", Toast.LENGTH_SHORT).show()
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
        //var ingredientesBase = ArrayList<IngredienteBase>()
        //var ingredientesExtra = ArrayList<IngredienteExtra>()

        /*constructor(context: Context, productosPersonalizado: ArrayList<ProductoPersonalizado>, ingredientesBase: ArrayList<IngredienteBase>, ingredientesExtra: ArrayList<IngredienteExtra>) {
            this.context = context
            this.productosPersonalizado = productosPersonalizado
            this.ingredientesBase = ingredientesBase
            this.ingredientesExtra = ingredientesExtra
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var productoPersonalizado = productosPersonalizado[position]
            var inflator = LayoutInflater.from(context)
            var vista = inflator.inflate(R.layout.activity_confirm_order, null)
            vista.product_name.setText(productoPersonalizado.nombrePersonalizado)
            vista.precio.setText((productoPersonalizado.precioExtra + productoPersonalizado.precioBasePersonalizado).toString())

            return vista
        }*/

        // -------------------------------------------------------------------------------------------------------------------
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
        // -------------------------------------------------------------------------------------------------------------------

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
