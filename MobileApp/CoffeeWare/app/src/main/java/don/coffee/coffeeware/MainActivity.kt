package don.coffee.coffeeware

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.producto_view.view.*
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.list
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {


    var JSON: JSONObject? = null
    var JSONarray: JsonArray? = null
    val categorias = ArrayList<Categoria>()
    var productos = ArrayList<Producto>()
    var adaptador: AdaptadorProductos? = null
    var categoriaActual = 1


    //Auxiliares
    var ing = IngredienteBase("Pastel")
    var porcionIngre = PorcionIngredienteBase(2, ing)
    var porciones = ArrayList<PorcionIngredienteBase>()
    var extra = IngredienteExtra(5.0,"Leche")
    var extras = ArrayList<IngredienteExtra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun cargarAuxiliares() {
            porciones.add(porcionIngre)
            porciones.add(porcionIngre)
            porciones.add(porcionIngre)

            extras.add(extra)
            extras.add(extra)
            extras.add(extra)
        }

        cargarCategorias("http://192.168.1.74:80/coffeeware/wsJSONConsultarListaCategorias.php")
        cargarAuxiliares()
        cargarAlimentos("http://192.168.1.74:80/coffeeware/wsJSONConsultarListaProductos.php")


        adaptador = AdaptadorProductos(this, productos)
        gridview_productos.adapter = adaptador

        btn_izquierda.setOnClickListener {
            when (categoriaActual) {
                0 -> categoriaActual = categorias.size - 1
                else -> categoriaActual--
            }

            desplegarTitulo(categoriaActual)

            if(categorias[categoriaActual].nombre.equals("Alimentos")){
                cargarAlimentos()
                adaptador = AdaptadorProductos(this, productos)
                gridview_productos.adapter = adaptador
            }
            if(categorias[categoriaActual].nombre.equals("Bebidas")){
                cargarBebidas()
                adaptador = AdaptadorProductos(this, productos)
                gridview_productos.adapter = adaptador
            }
            if(categorias[categoriaActual].nombre.equals("Postres")){
                cargarPostres()
                adaptador = AdaptadorProductos(this, productos)
                gridview_productos.adapter = adaptador
            }
        }

        btn_derecha.setOnClickListener {
            when (categoriaActual) {
                categorias.size - 1 -> categoriaActual = 0
                else -> categoriaActual++
            }
            desplegarTitulo(categoriaActual)

            if(categorias[categoriaActual].nombre.equals("Alimentos")){
                cargarAlimentos()
                adaptador = AdaptadorProductos(this, productos)
                gridview_productos.adapter = adaptador
            }
            if(categorias[categoriaActual].nombre.equals("Bebidas")){
                cargarBebidas()
                adaptador = AdaptadorProductos(this, productos)
                gridview_productos.adapter = adaptador
            }
            if(categorias[categoriaActual].nombre.equals("Postres")){
                cargarPostres()
                adaptador = AdaptadorProductos(this, productos)
                gridview_productos.adapter = adaptador
            }
        }

    }


    fun desplegarTitulo(index: Int) {
        textview_titulo.text = categorias[index].nombre
    }

    fun cargarCategorias(URL:String) {

        val jsonA = JsonObjectRequest(Request.Method.GET,URL,null,Response.Listener { response ->
            var JSON = response.getJSONArray("categoria")
            val gson = Gson()
            for(i in 0..JSON.length()-1){

                    var categoriaJson = JSON[i].toString()
                    var categoriaTemp:Categoria = gson.fromJson(categoriaJson,Categoria::class.java)
                var id:Int = categoriaTemp.id
                var nombre:String = categoriaTemp.nombre
                    categorias.add(Categoria(nombre,id))
                }
            },Response.ErrorListener { error ->
            Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()
        })
       var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonA)
    }

    fun cargarAlimentos(URL:String) {

        var lecturaProductos = ArrayList<String>()
        val jsonobject = JsonObjectRequest(Request.Method.GET,URL,null,Response.Listener { response ->

            for( i in 0..response.length()-1) {
                var jsonObj = response.getJSONArray("producto")
                lecturaProductos.add(jsonObj[i].toString())
            }

            textview_numeroproductos.setText("Entro aca")


        },Response.ErrorListener { error ->
            Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()
        })

        textview_numeroproductos.setText("2")
        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonobject)

        var tam = (lecturaProductos.size)-1
        textview_numeroproductos.setText(tam.toString())
        for(i in 0..tam){

            val gson = Gson()
            var ultimo:Int = (lecturaProductos[i].lastIndex)-2
            var categoria:Categoria =Categoria("Alimentos",0)

            when (lecturaProductos[ultimo].toInt()){
                0 ->  categoria = Categoria("Alimentos",0)
                1 ->  categoria = Categoria("Bebidas",1)
                2 ->  categoria = Categoria("Postres",2)
            }
            val productoTemp:Producto =  gson.fromJson<Producto>(lecturaProductos[i],Producto::class.java)
            productoTemp.categoria = categoria
            productoTemp.image = R.drawable.image_icon
            productoTemp.descripcion="Descripcion"
            productoTemp.ingredientesBase = porciones
            productoTemp.ingredientesExtra=extras

            textview_numeroproductos.setText(productoTemp.nombre)
            productos.add(productoTemp)
        }


    }
/*
    fun cargarBebidas() {
        productos.removeAll(productos)

        productos.add(
            Producto(
                "Caffe Americano",
                categorias.get(1),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Caffe Late",
                categorias.get(1),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Malteada",
                categorias.get(1),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Frappe",
                categorias.get(1),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Licuado",
                categorias.get(1),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Chocomilk",
                categorias.get(1),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
    }

    fun cargarPostres() {
        productos.removeAll(productos)

        productos.add(
            Producto(
                "Brownie",
                categorias.get(2),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Dona",
                categorias.get(2),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Galletass",
                categorias.get(2),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Muffin",
                categorias.get(2),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Pan Dulce",
                categorias.get(2),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Rebanada de pastel",
                categorias.get(2),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
    }
*/
    class AdaptadorProductos : BaseAdapter {
        var productos = ArrayList<Producto>()
        var contexto: Context? = null

        constructor(contexto: Context, productos: ArrayList<Producto>) {
            this.contexto = contexto
            this.productos = productos
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var producto = productos[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.producto_view, null)

            vista.textview_nombre.setText(producto.nombre)
            vista.btn_producto.setImageResource(producto.image)
            vista.textview_descripcion.setText(producto.descripcion)

            vista.btn_producto.setOnClickListener{
                var nombre = vista.textview_nombre.text
                val iterator = productos.iterator()

                iterator.forEach {
                    if(it.nombre?.equals(nombre)!!){
                        var prod: Producto = it
                        val intent = Intent(contexto, PersonalizarProductoActivity::class.java)
                        intent.putExtra("nombre",prod.nombre)
                        intent.putExtra("preciobase",prod.precioBase)
                        intent.putParcelableArrayListExtra("ingsbase",prod.ingredientesBase)
                        intent.putParcelableArrayListExtra("ingsextra",prod.ingredientesExtra)
                        contexto!!.startActivity(intent)
                    }
                }

            }

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
}