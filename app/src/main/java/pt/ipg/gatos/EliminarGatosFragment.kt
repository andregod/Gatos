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
import pt.ipg.gatos.databinding.FragmentEliminarGatosBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EliminarGatosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminarGatosFragment : Fragment() {
    private lateinit var gato: Gato
    private var _binding: FragmentEliminarGatosBinding? = null

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

        _binding = FragmentEliminarGatosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        gato = EliminarGatosFragmentArgs.fromBundle(requireArguments()).gato

        binding.textViewNome.text = gato.nome
        binding.textViewPeso.text = gato.peso.toString()
        binding.textViewRaca.text = gato.raca.nomeRaca
        if (gato.dataNascimento != null) {
            binding.textViewDataNascimento.text = DateFormat.format("yyyy-MM-dd", gato.dataNascimento)
        }

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
                voltaListaGatos()
                true
            }
            else -> false
        }
    }

    private fun voltaListaGatos() {
        findNavController().navigate(R.id.action_eliminarGatos_to_ListaGatosFragment)
    }

    private fun eliminar() {
        val enderecoGato = Uri.withAppendedPath(GatosContentProvider.ENDERECO_GATOS, gato.id.toString())
        val numGatosEliminados = requireActivity().contentResolver.delete(enderecoGato, null, null)

        if (numGatosEliminados == 1) {
            Toast.makeText(requireContext(), getString(R.string.gato_eliminado_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaGatos()
        } else {
            Snackbar.make(binding.textViewNome, getString(R.string.erro_eliminar_gato), Snackbar.LENGTH_INDEFINITE)
        }

    }
}