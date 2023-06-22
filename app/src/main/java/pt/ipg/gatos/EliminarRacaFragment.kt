package pt.ipg.gatos

import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.gatos.databinding.FragmentEliminarRacaBinding

class EliminarRacaFragment : Fragment() {
    private lateinit var raca: Raca
    private var _binding: FragmentEliminarRacaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarRacaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        raca = EliminarRacaFragmentArgs.fromBundle(requireArguments()).raca
        activity.atualizaTitulo(R.string.apagar_raca_label)

        binding.textViewNomeRaca.text= raca.nomeRaca
        binding.textViewPorteRaca.text= raca.PorteRaca
        binding.textViewCorRaca.text = raca.corPrincipalRaca


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaRacas()
                true
            }
            else -> false
        }
    }

    private fun voltaListaRacas() {
        findNavController().navigate(R.id.action_eliminarRacaFragment_to_listaRacasFragment)
    }

    private fun eliminar() {
        val enderecoRaca = Uri.withAppendedPath(GatosContentProvider.ENDERECO_RACAS, raca.id.toString())
        val numRacasEliminadas = requireActivity().contentResolver.delete(enderecoRaca, null, null)

        if (numRacasEliminadas == 1) {
            Toast.makeText(requireContext(), getString(R.string.raca_eliminada_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaRacas()
        } else {
            Snackbar.make(binding.textViewNomeRaca, getString(R.string.erro_eliminar_raca), Snackbar.LENGTH_INDEFINITE)
        }
    }
}