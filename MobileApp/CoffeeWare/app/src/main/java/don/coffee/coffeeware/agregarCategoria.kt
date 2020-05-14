package don.coffee.coffeeware

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_agregar_categoria.*
import kotlinx.android.synthetic.main.activity_agregar_categoria.edtId
import kotlinx.android.synthetic.main.activity_agregar_categoria.edtNombre
import kotlinx.android.synthetic.main.activity_agregar_producto.*
import kotlinx.android.synthetic.main.activity_manejar_categoria.*

class agregarCategoria : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_categoria)

        var categoria = intent.getParcelableExtra<Categoria>("categoria")
        if (categoria != null){
            edtId.setText(categoria.ID)
            edtNombre.setText(categoria.nombre)
            textview_tituloCategoria.text = "Actualiar categoria"
            btn_categoriaAgregar.text = "Actualizar Categoria"
        }

        btn_categoriaAgregar.setOnClickListener {
            if (textview_tituloCategoria.text.toString().equals("Actualizar producto", true)){
                actualizarCategoria()
            } else {
                agregarCategoria()
            }

        }

    }

    private fun actualizarCategoria() {
        val url = "http://192.168.1.74:80/coffeeware/wsJSONActualizaCategoria.php?"

        var req = object:StringRequest(Request.Method.POST, url, Response.Listener { response ->
            if (response.toString().equals("actualiza", true)) {
                Toast.makeText(this, "ACTUALIZADO CON EXITO", Toast.LENGTH_SHORT).show()
                edtId.setText("")
                edtNombre.setText("")
            } else {
                Toast.makeText(this, "NO SE HA PODIDO ACTUALIZAR", Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener {
            Toast.makeText(this, "No se ha podido conectar a la Base de datos",Toast.LENGTH_SHORT).show()
        }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["ID"] = edtId.text.toString()
                params["nombre"] = edtNombre.text.toString()
                return params
            }
        }

        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(req)
    }

    fun agregarCategoria(){
        val url = "http://192.168.1.74:80/coffeeware/wsJSONRegistroCategorias.php?ID="+edtId.text.toString()+"&nombre="+edtNombre.text.toString()

        val agg = JsonObjectRequest(Request.Method.POST,url,null, Response.Listener { response ->
            Toast.makeText(applicationContext, "OPERACIÓN EXITOSA", Toast.LENGTH_SHORT).show()
            edtId.setText("")
            edtNombre.setText("")
        },Response.ErrorListener { error ->
            Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
        })

        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(agg)
    }
}
