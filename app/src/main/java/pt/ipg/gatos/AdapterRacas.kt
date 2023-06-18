package pt.ipg.gatos

import android.database.Cursor
import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterRacas(val fragment: ListaRacasFragment) : RecyclerView.Adapter<AdapterRacas.ViewHolderRaca>() {

    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolderRaca(contentor: View) : RecyclerView.ViewHolder(contentor) {
        private val textViewNomeRaca = contentor.findViewById<TextView>(R.id.textViewNomeRaca)
        private val textViewCorRaca = contentor.findViewById<TextView>(R.id.textViewCorRaca)
        private val textViewPorteRaca = contentor.findViewById<TextView>(R.id.textViewPorteRaca)


        init {
            contentor.setOnClickListener {
                ViewHolderSelecionado?.desSeleciona()
                seleciona()
                //Toast.makeText(fragment.requireContext(),"teste", Toast.LENGTH_SHORT).show() }

            }
        }

        internal var raca: Raca? = null
            set(value) {
                field = value
                textViewNomeRaca.text = raca?.nomeRaca ?: ""
                textViewCorRaca.text = raca?.corPrincipalRaca ?: ""
                textViewPorteRaca.text = raca?.PorteRaca ?: ""


            }

        fun seleciona() {
            ViewHolderSelecionado = this
            fragment.racaSelecionada = raca
            itemView.setBackgroundResource(R.color.item_selecionado)
        }

        fun desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white)
        }
    }

    private var ViewHolderSelecionado: AdapterRacas.ViewHolderRaca? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRacas.ViewHolderRaca {
        return ViewHolderRaca(
            fragment.layoutInflater.inflate(R.layout.item_raca, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: AdapterRacas.ViewHolderRaca, position: Int) {
        cursor!!.moveToPosition(position)
        holder.raca = Raca.fromCursor(cursor!!)

    }
}

