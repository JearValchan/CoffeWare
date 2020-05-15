package don.coffee.coffeeware

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.activity_personalizar_producto.*
import kotlinx.android.synthetic.main.ingredient_view.view.*
import kotlinx.android.synthetic.main.ingrediente_view.view.*
import kotlinx.android.synthetic.main.producto_orden.view.*


class PersonalizarProductoActivity : AppCompatActivity() {

    var adaptador: AdaptadorIngsBase? = null
    var adaptadorExtra: AdaptadorIngsExtra? = null
    var extra = IngredienteExtra("Valentina", 5.0)
    var extras = ArrayList<IngredienteExtra>()
    var ing = IngredienteBase("papas")
    var porcionIngre = PorcionIngredienteBase(20, ing)
    var porciones = ArrayList<PorcionIngredienteBase>()
    var ingredientes = ArrayList<PorcionIngredienteBase>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalizar_producto)

        val producto = intent.getParcelableExtra<Producto>("producto")
        val productoPersonalizado = ProductoPersonalizado(producto!!)
        productoPersonalizado.precioExtra = 25.0
        nombreProducto.text = productoPersonalizado.nombrePersonalizado
        precioBase.text = ""+productoPersonalizado.preciobase
        precioTotal.text = ""+(productoPersonalizado.precioBasePersonalizado+productoPersonalizado.precioExtra)

        //Agregando ingredientes
        porciones.add(porcionIngre)
        extras.add(extra);
        productoPersonalizado.ingredientesBasePersonalizado = porciones;
        productoPersonalizado.ingredientesExtraPersonalizado = extras;

        //Adapters
        adaptador = AdaptadorIngsBase(this, ingredientes)
        list_ingredientes.adapter = adaptador
        adaptadorExtra = AdaptadorIngsExtra(this, extras)
        list_extras.adapter = adaptadorExtra

        btn_anadirOrden.setOnClickListener {
            SessionData.ordenActual.add(productoPersonalizado)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        if(!SessionData.ordenActual.isEmpty()){
            ordenActual.adapter = AdaptadorOrden(this, SessionData.ordenActual)
        }

    }

    inner class AdaptadorIngsBase : BaseAdapter {
        var ingredientes = ArrayList<PorcionIngredienteBase>()
        var contexto: Context? = null
        var ing = IngredienteBase("papas")
        var porcionIngre = PorcionIngredienteBase(20, ing)


        constructor(contexto: Context, ingredientes: ArrayList<PorcionIngredienteBase>?) {
            this.contexto = contexto
            this.ingredientes.add(porcionIngre)
            if (!ingredientes.isNullOrEmpty()) {
                this.ingredientes = ingredientes
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var ingBase = ingredientes[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.ingrediente_view, null)

            var nombreIngrediente: String? = ingBase.ingrediente.nombre
            var cantidadIngrediente: Int = ingBase.cantidad

            vista.textview_nombreing.text = "$nombreIngrediente"
            vista.textview_cantidad.text = "$cantidadIngrediente"

            return vista
        }

        override fun getItem(position: Int): Any {
            return ingredientes[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return ingredientes.size
        }

    }

    class AdaptadorIngsExtra : BaseAdapter {
        var extras = ArrayList<IngredienteExtra>()
        var contexto: Context? = null
        var extra = IngredienteExtra("Valentina", 5.0)


        constructor(contexto: Context, extras: ArrayList<IngredienteExtra>?) {
            this.contexto = contexto
            this.extras.add(extra)
            if (!extras.isNullOrEmpty()) {
                this.extras = extras
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var extra = extras[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.ingrediente_view, null)

            var nombreIngrediente: String? = extra.nombre
            var cantidadIngrediente: Int = 0

            vista.textview_nombreing.text = "$nombreIngrediente"
            vista.textview_cantidad.text = "$cantidadIngrediente"

            return vista
        }

        override fun getItem(position: Int): Any {
            return extras[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return extras.size
        }

    }

    class AdaptadorOrden: BaseAdapter{
        lateinit var mContext:Context
        var ordenActual = ArrayList<ProductoPersonalizado>()

        constructor(mContext:Context, ordenActual:ArrayList<ProductoPersonalizado>){
            this.mContext = mContext
            this.ordenActual = ordenActual
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var productoPersonalizado = ordenActual[position]
            var inflater = mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.producto_orden, null)

            var ingredientesBase = productoPersonalizado.ingredientesBasePersonalizado
            var ingredientesExtra = productoPersonalizado.ingredientesExtraPersonalizado

            vista.list_ingredients.isGone = true
            vista.product_name.text = productoPersonalizado.nombrePersonalizado
            vista.precio.text = (productoPersonalizado.preciobase+productoPersonalizado.precioExtra).toString()
            var listaIngredientes = vista.list_ingredients

            for (ing in ingredientesBase){
                var vistaIngrediente = inflater.inflate(R.layout.ingredient_view, null)
                vistaIngrediente.nombreIngrediente.text = ing.ingrediente.nombre
                vistaIngrediente.cantidadIngrediente.text = ing.cantidad.toString()

                listaIngredientes.addView(vistaIngrediente)
            }

            for (ing in ingredientesExtra){
                var vistaIngrediente = inflater.inflate(R.layout.ingredient_view, null)
                vistaIngrediente.nombreIngrediente.text = ing.nombreExtra
                vistaIngrediente.cantidadIngrediente.text = 1.toString()

                listaIngredientes.addView(vistaIngrediente)
            }

            vista.product_name.setOnClickListener {
                vista.list_ingredients.isGone = !vista.list_ingredients.isGone
            }

            return vista

        }

        override fun getItem(position: Int): Any {
            return ordenActual[position]
        }

        override fun getItemId(position: Int): Long {
            return -1
        }

        override fun getCount(): Int {
            return ordenActual.size
        }

    }

}
