package pt.ipg.gatos

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import pt.ipg.gatos.databinding.FragmentEditarRacaBinding

private const val ID_LOADER_RACAS = 0

class EditarRacaFragment : Fragment() , LoaderManager.LoaderCallbacks<Cursor> {
    private var raca: Raca?= null
    private var _binding: FragmentEditarRacaBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarRacaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_RACAS, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val raca = EditarRacaFragmentArgs.fromBundle(requireArguments()).raca

        if (raca != null) {
            activity.atualizaTitulo(R.string.editar_raca_label)
            binding.editTextNomeRaca.setText(raca.nomeRaca)
            binding.editTextCorRaca.setText(raca.corPrincipalRaca)
            binding.editTextPorteRaca.setText(raca.PorteRaca)
        } else {
            activity.atualizaTitulo(R.string.nova_raca_label)
        }

        this.raca = raca
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
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
        findNavController().navigate(R.id.action_editarRacaFragment_to_listaRacasFragment)
    }
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            GatosContentProvider.ENDERECO_RACAS,
            TabelaRacas.CAMPOS,
            null, null,
            TabelaRacas.CAMPO_NOMERACA
        )
    }

    private fun guardar() {
        val nome = binding.editTextNomeRaca.text.toString()
        if (nome.isBlank()) {
            binding.editTextNomeRaca.error = getString(R.string.nome_obrigatorio)
            binding.editTextNomeRaca.requestFocus()
            return
        }

        val cor = binding.editTextCorRaca.text.toString()
        if (cor.isBlank()) {
            binding.editTextCorRaca.error = getString(R.string.cor_obrigatoria)
            binding.editTextCorRaca.requestFocus()
            return
        }

        val porte = binding.editTextPorteRaca.text.toString()
        if (porte.isBlank()) {
            binding.editTextPorteRaca.error = getString(R.string.porte_obrigatorio)
            binding.editTextPorteRaca.requestFocus()
            return
        }

        if (raca == null) {
            val raca = Raca(
                nome,
                cor,
                porte
            )
            insereRaca(raca)
        } else {
            val raca = raca!!
            raca.nomeRaca = nome
            raca.corPrincipalRaca = cor
            raca.PorteRaca = porte

            alteraRaca(raca)
        }
    }

    private fun alteraRaca(raca: Raca) {
        val enderecoRaca = Uri.withAppendedPath(GatosContentProvider.ENDERECO_RACAS, raca.id.toString())
        val racasAlteradas = requireActivity().contentResolver.update(enderecoRaca, raca.toContentValues(), null, null)

        if (racasAlteradas == 1) {
            Toast.makeText(requireContext(), R.string.raca_guardada_com_sucesso, Toast.LENGTH_LONG).show()
            voltaListaRacas()
        } else {
            binding.editTextNomeRaca.error = getString(R.string.erro_guardar_raca)
        }
    }
    private fun insereRaca(
        raca: Raca
    ) {

        val id = requireActivity().contentResolver.insert(
            GatosContentProvider.ENDERECO_RACAS,
            raca.toContentValues()
        )

        if (id == null) {
            binding.editTextNomeRaca.error = getString(R.string.erro_guardar_raca)
            return
        }
        Toast.makeText(
            requireContext(),
            getString(R.string.raca_guardada_com_sucesso),
            Toast.LENGTH_SHORT
        ).show()

        voltaListaRacas()
    }
    override fun onLoaderReset(loader: Loader<Cursor>) {
        //binding.spinnerRacas.adapter = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
    /*    if (data == null) {
            binding.spinnerRacas.adapter = null
            return
        }

        binding.spinnerRacas.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaRacas.CAMPO_NOMERACA),
            intArrayOf(android.R.id.text1),
            0
        )

        mostraRacaSelecionadaSpinner()
        */

    }

    private fun mostraRacaSelecionadaSpinner() {
       /* if (gato == null) return

        val idRaca = gato!!.raca.id

        val ultimaRaca = binding.spinnerRacas.count - 1
        for (i in 0..ultimaRaca) {
            if (idRaca == binding.spinnerRacas.getItemIdAtPosition(i)) {
                binding.spinnerRacas.setSelection(i)
                return
            }
        }*/
    }
    companion object {

    }

}