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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.producto_view.view.*

class MainActivity : AppCompatActivity() {

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

        cargarCategorias()
        cargarAuxiliares()
        cargarAlimentos()

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

    fun cargarCategorias() {
        categorias.add(Categoria("Alimentos"))
        categorias.add(Categoria("Bebidas"))
        categorias.add(Categoria("Postres"))
    }

    fun cargarAlimentos() {
        productos.removeAll(productos)

        productos.add(
            Producto(
                "Panini",
                categorias.get(0),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Hamburguesa",
                categorias.get(0),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Bownless",
                categorias.get(0),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Sandwich",
                categorias.get(0),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Hot Dog",
                categorias.get(0),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
        productos.add(
            Producto(
                "Pan Frances",
                categorias.get(0),
                R.drawable.image_icon,
                "Descripcion producto",
                25.50,
                porciones,
                extras
            )
        )
    }

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