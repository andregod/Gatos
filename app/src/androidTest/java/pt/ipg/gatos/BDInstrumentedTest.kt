package pt.ipg.gatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BDInstrumentedTest {
    private fun getAppContext() : Context =
        InstrumentationRegistry.getInstrumentation().targetContext
    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BdGatosOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        // Context of the app under test.

        val openHelper = BdGatosOpenHelper(getAppContext())
        val bd= openHelper.readableDatabase
        assert(bd.isOpen)

        val appContext = getAppContext()
        assertEquals("pt.ipg.gatos", appContext.packageName)
    }

    @Test

    fun consegueInserirRacas() {
        val bd = getWritableDatabase()
        val raca = Raca("Tuxedo", "Branco/Preto", "Médio")
        val id = insereRaca(bd, raca)
        assertNotEquals(-1, id )


    }

    @Test
    fun consegueInserirGatos() {
        val bd = getWritableDatabase()

        val raca = Raca("Tuxedo", "Branco/Preto", "Médio")
        insereRaca(bd, raca)

        val dataNasc= Calendar.getInstance()
        dataNasc.set(2023, 2, 1)

        val gato = Gato("Stella", "Branco/Preto", "Femea", dataNasc, 3, 1.1 ,"Andre Godinho", "Rua Raul de Matos" , "Medio", raca.id)
        insereGato(bd,gato)

        val dataNasc2= Calendar.getInstance()
        dataNasc2.set(2020, 3, 1)
        val gato2 = Gato("Félix", "Laranja", "Macho", dataNasc, 38, 4.0 ,"Vasco", "Rua Gambelas" , "Grande", raca.id)
        insereGato(bd, gato2)
    }

    @Test
    fun consegueLerGatos() {
        val bd = getWritableDatabase()

        val raca = Raca("Tuxedo", "Branco/Preto", "Medio")
        insereRaca(bd, raca)

        val dataNasc= Calendar.getInstance()
        dataNasc.set(2023, 2, 1)

        val gato = Gato("Stella", "Branco/Preto", "Femea", dataNasc, 3, 1.1 ,"Andre Godinho", "Rua Raul de Matos" , "Medio", raca.id)
        insereGato(bd,gato)

        val dataNasc2= Calendar.getInstance()
        dataNasc2.set(2020, 3, 1)
        val gato2 = Gato("Félix", "Laranja", "Macho", dataNasc, 38, 4.0 ,"Vasco", "Rua Gambelas" , "Grande", raca.id)
        insereGato(bd, gato2)

        val tabelaGatos = TabelaGatos(bd)

        val cursor = tabelaGatos.consultar(
            TabelaGatos.CAMPOS,
            null,null,null,null,TabelaCategorias.CAMPO_NOMERACA
        )

        assert(cursor.moveToNext())

        val gatoBD = Gato.fromCursor(cursor)

        assertEquals(gato, gatoBD)

        val cursorTodosGatos = tabelaGatos.consultar(
            TabelaGatos.CAMPOS,
            null,null,null,null,TabelaGatos.CAMPO_NOME
        )

        assert(cursorTodosGatos.count>1)


    }

    private fun insereGato(
        bd: SQLiteDatabase,
        gato: Gato ) {

        gato.id = TabelaGatos(bd).insere(gato.toContentValues())
        assertNotEquals(-1, gato.id)
    }

    private fun insereRaca(
        bd: SQLiteDatabase,
        raca: Raca) {

        raca.id = TabelaCategorias(bd).insere(raca.toContentValues())
        assertNotEquals(-1, raca.id)
    }

    @Test
    fun consegueLerRacas() {
        val bd = getWritableDatabase()

        val racaTux= Raca("Tuxedo", "Branco/Preto","Médio")
        insereRaca(bd,racaTux)

        val racaPersa = Raca("Persa", "Laranja/Branco", "Médio")
        insereRaca(bd, racaPersa)

        val cursor = TabelaCategorias(bd).consultar(
            TabelaCategorias.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(racaTux.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val categBD = Raca.fromCursor(cursor)

        assertEquals(racaTux, categBD)

        val tabelaCategorias = TabelaCategorias(bd)
        val cursorTodasCategorias = tabelaCategorias.consultar(
            TabelaCategorias.CAMPOS,
            null,null,null,null,TabelaCategorias.CAMPO_NOMERACA
        )

        assert(cursorTodasCategorias.count>1)
    }


    @Test
    fun consegueAlterarRacas()  {
        val bd = getWritableDatabase()

        val raca = Raca("Maine", "Pequeno", "Branco")
        insereRaca(bd,raca)

        raca.nomeRaca = "Poesia"
        //Adicionar restantes campos


        val registosAlterados = TabelaCategorias(bd).alterar(
            raca.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(raca.id.toString()))


            assertEquals(1, registosAlterados)

    }


    @Test
    fun consegueAlterarGatos()  {
        val bd = getWritableDatabase()

        val raca = Raca("Maine", "Pequeno", "Branco")
        insereRaca(bd,raca)

        val dataNasc= Calendar.getInstance()
        dataNasc.set(2023, 2, 1)
        val gato = Gato("Stella", "Branco/Preto", "Femea", dataNasc, 3, 1.1 ,"Andre Godinho", "Rua Raul de Matos" , "Medio", raca.id)
        insereGato(bd,gato)


        val novaDataNasc = Calendar.getInstance()
        novaDataNasc.set(2023,2,2)


        gato.id = gato.id
        gato.nome = "Bloom"
        gato.cor = "Castanho"
        gato.genero = "Macho"
        gato.dataNascimento = novaDataNasc
        gato.idade = 4
        gato.peso = 4.5
        gato.nomeDono = "Noel"
        gato.morada = "Guarda"
        gato.porteGato = "Grande"
        //Adicionar restantes campos


        val registosAlterados = TabelaGatos(bd).alterar(
            gato.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(gato.id.toString()))


            assertEquals(1, registosAlterados)

    }

    @Test

    fun consegueApagarRacas() {
        val bd = getWritableDatabase()

        val raca = Raca("Bengal", "Laranja", "Pequeno")
        insereRaca(bd,raca)


        val registosAlterados = TabelaCategorias(bd).eliminar(
            "${BaseColumns._ID}=?",
            arrayOf(raca.id.toString()))


        assertEquals(1, registosAlterados)
    }

    @Test

    fun consegueApagarGatos() {
        val bd = getWritableDatabase()

        val raca = Raca("Burmes", "Branco/Castanho", "Pequeno")
        insereRaca(bd,raca)

        val novaDataNasc = Calendar.getInstance()
        novaDataNasc.set(2023,2,2)

        val gato = Gato("TesteBurmes", "Branco/Preto", "Femea", novaDataNasc, 3, 1.1 ,"Andre Godinho", "Rua Raul de Matos" , "Medio", raca.id)
        insereGato(bd,gato)

        val registosAlterados = TabelaGatos(bd).eliminar(
            "${BaseColumns._ID}=?",
            arrayOf(gato.id.toString()))


        assertEquals(1, registosAlterados)
    }




    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdGatosOpenHelper(getAppContext())
        val bd = openHelper.writableDatabase
        return bd
    }



}