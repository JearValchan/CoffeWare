package com.example.testapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_lista_productos2.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var edtId:EditText
    lateinit var edtTipo:EditText
    lateinit var edtNombre:EditText
    lateinit var edtPrecioBase:EditText
    lateinit var edtCategoria:EditText

    lateinit var btnAgregar:Button
    lateinit var btnBuscar:Button
    lateinit var btnEditar:Button
    lateinit var btnEliminar:Button
    lateinit var btnLista:Button

    lateinit var request: RequestQueue
    lateinit var jsonObjectRequest: JsonObjectRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtId = findViewById(R.id.idProducto)
        edtTipo = findViewById(R.id.tipoProducto)
        edtNombre = findViewById(R.id.nombreProducto)
        edtPrecioBase = findViewById(R.id.precioBaseProducto)
        edtCategoria = findViewById(R.id.idCategoria)
        btnAgregar = findViewById(R.id.btnAgregar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnLista = findViewById(R.id.btnLista)

        request = Volley.newRequestQueue(applicationContext)

        btnAgregar.setOnClickListener {
            servicioAgregarProducto()
        }

        btnBuscar.setOnClickListener{
            servicioConsultarProducto()
        }

        btnLista.setOnClickListener {
            var intent:Intent = Intent(this, ListaProductosActivity::class.java)
            servicioListaProductos()
            /*
            startActivity(intent)
            */
        }

        btnEliminar.setOnClickListener {
            servicioEliminaProducto()
        }

        btnEditar.setOnClickListener {
            servicioActualizarProducto()
        }
    }

    private fun servicioActualizarProducto(){
        /*
        var url: String="http://192.168.0.13:80/coffeeware/wsJSONRegistroProducto.php?ID="+edtId.text.toString()+"&producto_type="+edtTipo.text.toString()+"&nombre="+edtNombre.text.toString()+"&preciobase="+edtPrecioBase.text.toString()+"&id_categoria="+edtCategoria.text.toString()
        url=url.replace(" ","%20")

        jsonObjectRequest= JsonObjectRequest(Request.Method.POST,url,null,
            Response.Listener<JSONObject?> {
                Toast.makeText(applicationContext, "OPERACIÓN EXITOSA", Toast.LENGTH_SHORT).show()
                edtId.setText("")
                edtTipo.setText("")
                edtNombre.setText("")
                edtPrecioBase.setText("")
                edtCategoria.setText("")
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, toString(), Toast.LENGTH_SHORT).show()
            }
        )
        request.add(jsonObjectRequest)
        */

        var url:String = "http://192.168.0.13:80/coffeeware/wsJSONActualizarProducto.php?"
        val jsonObject = JSONObject()
        jsonObject.put("ID", edtId.text.toString())
        jsonObject.put("producto_type", edtTipo.text.toString())
        jsonObject.put("nombre", edtNombre.text.toString())
        jsonObject.put("preciobase", edtPrecioBase.text.toString())
        jsonObject.put("id_categoria", edtCategoria.text.toString())

        val req = JsonObjectRequest(Request.Method.POST, url, jsonObject, Response.Listener {response ->
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
            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
        })

        request.add(req)
    }

    private fun servicioEliminaProducto(){
        var url:String="http://192.168.0.13:80/coffeeware/wsJSONEliminarProducto.php?ID="+edtId.text.toString()
        url = url.replace(" ","%20")

        var stringRequest = StringRequest(url, Response.Listener<String> { response ->
            if (response.trim().equals("elimina", true)){
                Toast.makeText(applicationContext, "ELIMINADO CON EXITO", Toast.LENGTH_SHORT).show()
                edtId.setText("")
                edtTipo.setText("")
                edtNombre.setText("")
                edtPrecioBase.setText("")
                edtCategoria.setText("")
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
        request.add(stringRequest)
    }

    private fun servicioConsultarProducto(){
        var url:String="http://192.168.0.13/coffeeware/wsJSONConsultarProducto.php?ID="+edtId.text.toString()
        url=url.replace(" ","%20")

        jsonObjectRequest= JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response ->

                val producto = Producto()

                val json:JSONArray = response.optJSONArray("producto")
                var jsonObject:JSONObject
                try {
                    jsonObject= json.getJSONObject(0)
                    producto.producto_type = jsonObject.optString("producto_type")
                    producto.nombre = jsonObject.optString("nombre")
                    producto.preciobase = jsonObject.optString("preciobase").toFloat()
                    producto.id_categoria = jsonObject.optString("id_categoria").toInt()
                } catch (e: JSONException){
                    e.printStackTrace()
                }

                edtTipo.setText(producto.producto_type)
                edtNombre.setText(producto.nombre)
                edtPrecioBase.setText(producto.preciobase.toString())
                edtCategoria.setText(producto.id_categoria.toString())
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            })
        request.add(jsonObjectRequest)

    }

    private fun servicioAgregarProducto() {
        var url: String="http://192.168.0.13:80/coffeeware/wsJSONRegistroProducto.php?ID="+edtId.text.toString()+"&producto_type="+edtTipo.text.toString()+"&nombre="+edtNombre.text.toString()+"&preciobase="+edtPrecioBase.text.toString()+"&id_categoria="+edtCategoria.text.toString()
        url=url.replace(" ","%20")

        jsonObjectRequest= JsonObjectRequest(Request.Method.POST,url,null,
            Response.Listener<JSONObject?> {
                Toast.makeText(applicationContext, "OPERACIÓN EXITOSA", Toast.LENGTH_SHORT).show()
                edtId.setText("")
                edtTipo.setText("")
                edtNombre.setText("")
                edtPrecioBase.setText("")
                edtCategoria.setText("")
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            }
        )
        request.add(jsonObjectRequest)
    }

    private fun servicioListaProductos() {
        var url: String="http://192.168.0.13/coffeeware/wsJSONConsultarListaProductos.php"
        var productos: ArrayList<Producto> = ArrayList()

        jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {response ->
                Toast.makeText(applicationContext, ""+response,Toast.LENGTH_SHORT).show()
                /*
                var producto:Producto
                var json: JSONArray = response.optJSONArray("producto")

                try {
                    for (i in 0..(json.length()-1)){
                        producto = Producto()
                        var jsonObject:JSONObject = json.getJSONObject(i)

                        producto.idProducto = jsonObject.optString("ID").toInt()
                        producto.producto_type = jsonObject.optString("producto_type")
                        producto.nombre = jsonObject.optString("nombre")
                        producto.preciobase = jsonObject.optString("preciobase").toFloat()
                        producto.id_categoria = jsonObject.optString("id_categoria").toInt()
                        productos.add(producto)
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                }

                intent.putExtra("list", productos)
                */
            },Response.ErrorListener {
                Toast.makeText(applicationContext, toString(), Toast.LENGTH_SHORT).show()
            }
            )
        request.add(jsonObjectRequest)
    }

}
