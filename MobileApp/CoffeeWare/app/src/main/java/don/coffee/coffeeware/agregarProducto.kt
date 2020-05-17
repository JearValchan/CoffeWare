package don.coffee.coffeeware

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_agregar_producto.*
import org.json.JSONObject

class agregarProducto : AppCompatActivity() {

    var producto:Producto? =  null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

         producto = intent.getParcelableExtra<Producto>("producto")
        if(producto!=null){
            edtId.setText(producto!!.ID.toString())
            edtTipo.setText(producto!!.producto_type)
            edtNombre.setText(producto!!.nombre)
            edtPrecioBase.setText(producto!!.preciobase.toString())
            edtCategoria.setText(producto!!.categoria.ID.toString())
            btn_aceptaragregar.setText("Actualizar producto")
            textview_titulo.setText("Actualizar producto")
        }

        btn_aceptaragregar.setOnClickListener {
            if(textview_titulo.text.toString()=="Actualizar producto"){
                actualizarProducto()
            }else{
                agregarProducto()
            }

        }

    }

    fun agregarProducto(){
        var url: String="http://192.168.0.13:80/coffeeware/wsJSONRegistroProducto.php?ID="+edtId.text.toString()+"&producto_type="+edtTipo.text.toString()+"&nombre="+edtNombre.text.toString()+"&preciobase="+edtPrecioBase.text.toString()+"&id_categoria="+edtCategoria.toString()
        val jsonobject= JsonObjectRequest(
            Request.Method.POST,url,null,
            Response.Listener<JSONObject?> {
                Toast.makeText(applicationContext, "OPERACIÓN EXITOSA", Toast.LENGTH_SHORT).show()
                edtId.setText("")
                edtTipo.setText("")
                edtNombre.setText("")
                edtPrecioBase.setText("")
                edtCategoria.setText("")

                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            }
        )
        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonobject)
    }

    fun actualizarProducto(){

        var url: String="http://192.168.0.13:80/coffeeware/wsJSONActualizarProducto.php"
        val req = object:StringRequest(Request.Method.POST, url, Response.Listener { response ->
            if (response.toString().trim().equals("actualiza", true)){
                Toast.makeText(applicationContext, "ACTUALIZADO CON EXITO", Toast.LENGTH_SHORT).show()
                edtId.setText("")
                edtTipo.setText("")
                edtNombre.setText("")
                edtPrecioBase.setText("")
                edtCategoria.setText("")

            }else{
                Toast.makeText(applicationContext, "No se ha actualizado", Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener {
            Toast.makeText(applicationContext, "No se ha podido conectar a la Base de datos", Toast.LENGTH_SHORT)
        }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["ID"] = edtId.text.toString()
                params["producto_type"] = edtTipo.text.toString()
                params["nombre"] = edtNombre.text.toString()
                params["preciobase"] = edtPrecioBase.text.toString()
                params["id_categoria"] = edtCategoria.text.toString()
                return params
            }
        }

        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(req)
    }

}
