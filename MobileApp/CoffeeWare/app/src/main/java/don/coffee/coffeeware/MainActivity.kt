package don.coffee.coffeeware

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.producto_view.view.*

class MainActivity : AppCompatActivity() {

    val categorias = ArrayList<Categoria>()
    var productos = ArrayList<Producto>()
    var adaptador: AdaptadorProductos? = null
    var categoriaActual = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cargarCategorias()
        cargarAlimentos()

        adaptador = AdaptadorProductos(this, productos)
        gridview_productos.adapter = adaptador

        btn_izquierda.setOnClickListener{
            when(categoriaActual){
                0 -> categoriaActual = categorias.size -1
                else -> categoriaActual --
            }

            desplegarTitulo(categoriaActual)
        }

        btn_derecha.setOnClickListener{
            when(categoriaActual){
                categorias.size -1 -> categoriaActual = 0
                else -> categoriaActual ++
            }

            desplegarTitulo(categoriaActual)
        }

    }

    fun desplegarTitulo(index: Int){
        textview_titulo.text = categorias[index].nombre
    }

    fun cargarCategorias(){
        categorias.add(Categoria("Alimentos"))
        categorias.add(Categoria("Bebidas"))
        categorias.add(Categoria("Postres"))
    }

    fun cargarAlimentos(){
        productos.add(Producto("Panini",R.drawable.image_icon))
        productos.add(Producto("Hamburguesa",R.drawable.image_icon))
        productos.add(Producto("Bownless",R.drawable.image_icon))
        productos.add(Producto("Sandwich",R.drawable.image_icon))
        productos.add(Producto("Hot Dog",R.drawable.image_icon))
        productos.add(Producto("Pan Frances",R.drawable.image_icon))
    }

    fun cargarBebidas(){
        productos.add(Producto("Caffe Americano",R.drawable.image_icon))
        productos.add(Producto("Caffe Late",R.drawable.image_icon))
        productos.add(Producto("Malteada",R.drawable.image_icon))
        productos.add(Producto("Frappe",R.drawable.image_icon))
        productos.add(Producto("Licuado",R.drawable.image_icon))
        productos.add(Producto("Chocomilk",R.drawable.image_icon))
    }

    fun cargarPostres(){
        productos.add(Producto("Brownie",R.drawable.image_icon))
        productos.add(Producto("Dona",R.drawable.image_icon))
        productos.add(Producto("Galletass",R.drawable.image_icon))
        productos.add(Producto("Muffin",R.drawable.image_icon))
        productos.add(Producto("Pan Dulce",R.drawable.image_icon))
        productos.add(Producto("Rebanada de pastel",R.drawable.image_icon))
    }

    class AdaptadorProductos: BaseAdapter {
        var productos = ArrayList<Producto>()
        var contexto: Context? = null

        constructor(contexto: Context, productos: ArrayList<Producto>){
            this.contexto = contexto
            this.productos = productos
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var producto = productos[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.producto_view,null)

            vista.btn_producto.setImageResource(producto.image)

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
