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
import kotlinx.android.synthetic.main.activity_personalizar_producto.view.*
import kotlinx.android.synthetic.main.ingredient_view.view.*
import kotlinx.android.synthetic.main.ingrediente_view.view.*
import kotlinx.android.synthetic.main.producto_orden.view.*
import kotlinx.android.synthetic.main.producto_view.*


class PersonalizarProductoActivity : AppCompatActivity() {

    var adaptador: AdaptadorIngsBase? = null
    var adaptadorExtra: AdaptadorIngsExtra? = null
    var extra = IngredienteExtra("Valentina", 5.0)
    var extras = ArrayList<IngredienteExtra>()
    var ing = IngredienteBase("papas")
    var porcionIngre = PorcionIngredienteBase(20, ing)
    var porciones = ArrayList<PorcionIngredienteBase>()



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
        adaptador = AdaptadorIngsBase(this, productoPersonalizado)
        list_ingredientes.adapter = adaptador
        adaptadorExtra = AdaptadorIngsExtra(this, productoPersonalizado)
        list_extras.adapter = adaptadorExtra

        btn_anadirOrden.setOnClickListener {
            SessionData.ordenActual.add(productoPersonalizado)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        if(!SessionData.ordenActual.isEmpty()){
            ordenActual.adapter = AdaptadorOrden(this, SessionData.ordenActual)
        }

        costoTotal.text = obtenerPrecioFinal().toString()

    }

    fun obtenerPrecioFinal(): Double{
        var total = 0.0
        for(x in SessionData.ordenActual){
            total += x.preciobase+x.precioExtra
        }
        return total
    }

    inner class AdaptadorIngsBase : BaseAdapter {
        var ingredientes = ArrayList<PorcionIngredienteBase>()
        var contexto: Context? = null


        constructor(contexto: Context, productoPersonalizado: ProductoPersonalizado) {
            this.contexto = contexto
            this.ingredientes = productoPersonalizado.ingredientesBasePersonalizado
            if (!ingredientes.isNullOrEmpty()) {
                this.ingredientes = ingredientes
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var ingBase = ingredientes[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.ingrediente_view, null)

            vista.textview_nombreing.text = ingBase.ingrediente.nombre
            vista.textview_cantidad.text = ingBase.cantidad.toString()

            vista.add.setOnClickListener {
                ingBase.cantidad++
                vista.textview_cantidad.text = ingBase.cantidad.toString()
            }

            vista.remove.setOnClickListener {
                if (ingBase.cantidad > 0){
                    ingBase.cantidad--
                    vista.textview_cantidad.text = ingBase.cantidad.toString()
                }
            }

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

    inner class AdaptadorIngsExtra : BaseAdapter {
        var extras = ArrayList<IngredienteExtra>()
        var contexto: Context? = null


        constructor(contexto: Context, productoPersonalizado: ProductoPersonalizado) {
            this.contexto = contexto
            this.extras = productoPersonalizado.ingredientesExtraPersonalizado
            if (!extras.isNullOrEmpty()) {
                this.extras = extras
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var extra = extras[position]
            var inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflater.inflate(R.layout.ingrediente_view, null)

            var cantidad = 0
            for (cont in extras){
                if (cont.nombreExtra == extra.nombreExtra) cantidad++
            }

            vista.textview_nombreing.text = extra.nombreExtra

            vista.add.setOnClickListener {
                extras.add(extra)
                notifyDataSetChanged()
            }

            vista.remove.setOnClickListener {
                if (cantidad > 1){
                    extras.remove(extra)
                    notifyDataSetChanged()
                }
            }

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

    inner class AdaptadorOrden: BaseAdapter{
        var mContext:Context
        var ordenActual = ArrayList<ProductoPersonalizado>()

        constructor(mContext:Context, ordenActual:ArrayList<ProductoPersonalizado>){
            this.mContext = mContext
            this.ordenActual = ordenActual
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var productoPersonalizado = ordenActual[position]
            var inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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
