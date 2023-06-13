package pt.ipg.gatos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.ipg.gatos.databinding.FragmentNovoGatoBinding

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
    private var _binding: FragmentNovoGatoBinding? = null

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

        _binding = FragmentNovoGatoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        gato = EliminarGatosFragmentArgs.fromBundle(requireArguments()).gato

        //binding.textViewNome.text = gato.nome
        //binding.textViewNome.text = gato.nome
        //binding.textViewNome.text = gato.nome
        //binding.textViewNome.text = gato.nome
        //binding.textViewNome.text = gato.nome
        //binding.textViewNome.text = gato.nome
    }

    companion object {

    }

    private fun voltaListaGatos() {
        findNavController().navigate(R.id.action_eliminarGatos_to_ListaGatosFragment)
    }

    private fun eliminar() {}
}