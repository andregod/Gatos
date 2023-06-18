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
        binding.textViewIdade.text = gato.idade.toString()
        binding.textViewRaca.text = gato.raca.nomeRaca
        binding.textViewNomeDono.text = gato.nomeDono
        if (gato.dataNascimento != null) {
            binding.textViewDataNascimento.text = DateFormat.format("yyyy-MM-dd", gato.dataNascimento)
        }
        binding.textViewGenero.text= gato.genero
        binding.textViewPeso.text = gato.peso.toString()
        binding.textViewMorada.text = gato.morada
        binding.textViewPorte.text= gato.porteGato
        binding.textViewCor.text=gato.cor

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