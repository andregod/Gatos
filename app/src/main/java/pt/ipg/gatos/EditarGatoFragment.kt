package pt.ipg.gatos

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import pt.ipg.gatos.databinding.FragmentEditarGatoBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

private const val ID_LOADER_RACAS = 0
class EditarGatoFragment : Fragment() , LoaderManager.LoaderCallbacks<Cursor> {
    private var gato: Gato?= null
    private var _binding: FragmentEditarGatoBinding? = null
    private var dataNasc : Calendar? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarGatoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarViewDataNasc.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            if (dataNasc == null) dataNasc = Calendar.getInstance()
            dataNasc!!.set(year, month, dayOfMonth)
        }

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_RACAS, null, this)


        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val gato = EditarGatoFragmentArgs.fromBundle(requireArguments()).gato

        if (gato != null) {
            activity.atualizaTitulo(R.string.editar_gato_label)
            binding.editTextNome.setText(gato.nome)
            binding.editTextPeso.setText(gato.peso.toString())
            binding.editTextIdade.setText(gato.idade.toString())
            binding.editTextCor.setText(gato.cor)
            binding.editTextGenero.setText(gato.genero)
            binding.editTextMorada.setText(gato.morada)
            binding.editTextPorte.setText(gato.porteGato)
            binding.editTextNomeDono.setText(gato.nomeDono)
            if (gato.dataNascimento != null) {
                //binding.editTextDataNasc.setText(
                   // DateFormat.format("yyyy-MM-dd", gato.dataNascimento)
                //)

                    dataNasc = gato.dataNascimento
                    binding.calendarViewDataNasc.date = dataNasc!!.timeInMillis

            }
        } else {
            activity.atualizaTitulo(R.string.novo_gato_label)
        }

        this.gato = gato
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
                voltaListaGatos()
                true
            }

            else -> false
        }
    }

    private fun voltaListaGatos() {
        findNavController().navigate(R.id.action_editarGatoFragment_to_ListaGatosFragment)
    }

    private fun guardar() {
        val nome = binding.editTextNome.text.toString()
        if (nome.isBlank()) {
            binding.editTextNome.error = getString(R.string.nome_obrigatorio)
            binding.editTextNome.requestFocus()
            return
        }

        val peso = binding.editTextPeso.text.toString()
        if (peso.isBlank()) {
            binding.editTextPeso.error = getString(R.string.peso_obrigatorio)
            binding.editTextPeso.requestFocus()
            return
        }

        val idade = binding.editTextIdade.text.toString()
        if (idade.isBlank()) {
            binding.editTextIdade.error = getString(R.string.idade_obrigatoria)
            binding.editTextIdade.requestFocus()
            return
        }

        val cor = binding.editTextCor.text.toString()
        if (cor.isBlank()) {
            binding.editTextCor.error = getString(R.string.cor_obrigatoria)
            binding.editTextCor.requestFocus()
            return
        }

        val genero = binding.editTextGenero.text.toString()
        if (genero.isBlank()) {
            binding.editTextGenero.error = getString(R.string.genero_obrigatorio)
            binding.editTextGenero.requestFocus()
            return
        }

        val morada = binding.editTextMorada.text.toString()
        if (morada.isBlank()) {
            binding.editTextMorada.error = getString(R.string.morada_obrigatoria)
            binding.editTextMorada.requestFocus()
            return
        }

        val porte = binding.editTextPorte.text.toString()
        if (porte.isBlank()) {
            binding.editTextPorte.error = getString(R.string.porte_obrigatorio)
            binding.editTextPorte.requestFocus()
            return
        }

        val nomedono = binding.editTextNomeDono.text.toString()
        if (nomedono.isBlank()) {
            binding.editTextNomeDono.error = getString(R.string.dono_obrigatorio)
            binding.editTextNomeDono.requestFocus()
            return
        }
        val racaId = binding.spinnerRacas.selectedItemId

        if (gato == null) {
            val gato = Gato(
                nome,
                cor,
                genero,
                dataNasc,
                idade.toInt(),
                peso.toDouble(),
                nomedono,
                morada,
                porte,
                Raca("?", "?", "?", racaId),
            )
            insereGato(gato)
        } else {
            val gato = gato!!
            gato.nome = nome
            gato.cor = cor
            gato.genero = genero
            gato.dataNascimento = dataNasc
            gato.idade = idade.toInt()
            gato.peso = peso.toDouble()
            gato.nomeDono = nomedono
            gato.morada = morada
            gato.porteGato = porte
            gato.raca = Raca("?", "?", "?",racaId)

            alteraGato(gato)
        }
    }

        private fun alteraGato(gato: Gato) {
            val enderecoGato = Uri.withAppendedPath(GatosContentProvider.ENDERECO_GATOS, gato.id.toString())
            val gatosAlterados = requireActivity().contentResolver.update(enderecoGato, gato.toContentValues(), null, null)

            if (gatosAlterados == 1) {
                Toast.makeText(requireContext(), R.string.gato_guardado_com_sucesso, Toast.LENGTH_LONG).show()
                voltaListaGatos()
            } else {
                binding.editTextNome.error = getString(R.string.erro_guardar_gato)
            }
        }

        private fun insereGato(
            gato: Gato
        ) {

        val id = requireActivity().contentResolver.insert(
            GatosContentProvider.ENDERECO_GATOS,
            gato.toContentValues()
        )

        if (id == null) {
            binding.editTextNome.error = getString(R.string.erro_guardar_gato)
            return
        }
            Toast.makeText(
                requireContext(),
                getString(R.string.gato_guardado_com_sucesso),
                Toast.LENGTH_SHORT
            ).show()

            voltaListaGatos()
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            GatosContentProvider.ENDERECO_RACAS,
            TabelaRacas.CAMPOS,
            null, null,
            TabelaRacas.CAMPO_NOMERACA
        )
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        binding.spinnerRacas.adapter = null
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data == null) {
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
    }

    private fun mostraRacaSelecionadaSpinner() {
        if (gato == null) return

        val idRaca = gato!!.raca.id

        val ultimaRaca = binding.spinnerRacas.count - 1
        for (i in 0..ultimaRaca) {
            if (idRaca == binding.spinnerRacas.getItemIdAtPosition(i)) {
                binding.spinnerRacas.setSelection(i)
                return
            }
        }
    }
}