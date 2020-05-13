package don.coffee.coffeeware

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
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

        btn_categoriaAgregar.setOnClickListener {
            agregarCategoria()
        }

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
