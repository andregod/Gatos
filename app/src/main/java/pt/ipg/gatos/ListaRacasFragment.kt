package pt.ipg.gatos

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ipg.gatos.databinding.FragmentListaRacasBinding


private const val ID_LOADER_RACAS = 0

class ListaRacasFragment : Fragment() , LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentListaRacasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    var racaSelecionada: Raca? = null

        set(value) {
            field = value

            val mostrarEliminarAlterar = (value != null)

            val activity = activity as MainActivity
            activity.mostraOpcaoMenu(R.id.action_editar, mostrarEliminarAlterar)
            activity.mostraOpcaoMenu(R.id.action_eliminar, mostrarEliminarAlterar)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaRacasBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var adapterRacas: AdapterRacas? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterRacas = AdapterRacas(this)
        binding.recyclerViewRacas.adapter = adapterRacas
        binding.recyclerViewRacas.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this,)
        loader.initLoader(ID_LOADER_RACAS, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_racas
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        //if (id == ID_LOADER_GATOS) {
        return CursorLoader(
            requireContext(),
            GatosContentProvider.ENDERECO_RACAS,
            TabelaRacas.CAMPOS,
            null,
            null,
            TabelaRacas.CAMPO_NOMERACA
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterRacas!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterRacas!!.cursor = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.action_adicionar -> {
                adicionaRaca()
                true
            }
            R.id.action_editar -> {
                editarRaca()
                true
            }
            R.id.action_eliminar -> {
                eliminarRaca()
                true
            }
            else -> false
        }
    }

    private fun eliminarRaca() {
       val acao = ListaRacasFragmentDirections.actionListaRacasFragmentToEliminarRacaFragment(racaSelecionada!!)
        findNavController().navigate(acao)
    }

    private fun editarRaca() {
        val acao = ListaRacasFragmentDirections.actionListaRacasFragmentToEditarRacaFragment(racaSelecionada!!)
        findNavController().navigate(acao)
    }

    private fun adicionaRaca() {
       val acao = ListaRacasFragmentDirections.actionListaRacasFragmentToEditarRacaFragment(null)
        findNavController().navigate(acao)
    }

    companion object {

    }
}



