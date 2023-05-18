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

    fun consegueInserirCategorias() {
        val bd = getWritableDatabase()
        val categoria = Categoria("Tuxedo", "Branco/Preto", "Médio")
        val id = insereCategoria(bd, categoria)
        assertNotEquals(-1, id )


    }

    @Test
    fun consegueInserirLivros() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Tuxedo", "Branco/Preto", "Médio")
        insereCategoria(bd, categoria)

        val dataNasc= Calendar.getInstance()
        dataNasc.set(2023, 2, 1)

        val gato = Gato("Stella", "Branco/Preto", "Femea", dataNasc, 3, 1.1 ,"Andre Godinho", "Rua Raul de Matos" , "Medio", categoria.id)
        insereLivro(bd,gato)

        val dataNasc2= Calendar.getInstance()
        dataNasc2.set(2020, 3, 1)
        val gato2 = Gato("Félix", "Laranja", "Macho", dataNasc, 38, 4.0 ,"Vasco", "Rua Gambelas" , "Grande", categoria.id)
        insereLivro(bd, gato2)
    }

    @Test
    fun consegueLerLivros() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Tuxedo", "Branco/Preto", "Medio")
        insereCategoria(bd, categoria)

        val dataNasc= Calendar.getInstance()
        dataNasc.set(2023, 2, 1)

        val gato = Gato("Stella", "Branco/Preto", "Femea", dataNasc, 3, 1.1 ,"Andre Godinho", "Rua Raul de Matos" , "Medio", categoria.id)
        insereLivro(bd,gato)

        val dataNasc2= Calendar.getInstance()
        dataNasc2.set(2020, 3, 1)
        val gato2 = Gato("Félix", "Laranja", "Macho", dataNasc, 38, 4.0 ,"Vasco", "Rua Gambelas" , "Grande", categoria.id)
        insereLivro(bd, gato2)

        val tabelaGatos = TabelaGatos(bd)
        val cursor = tabelaGatos.consultar(
            TabelaGatos.CAMPOS,
            null,null,null,null,TabelaCategorias.CAMPO_NOMERACA
        )

        val gatoBD = Gato.fromCursor(cursor)

        assertEquals(gato, gatoBD)

        val cursorTodosGatos = tabelaGatos.consultar(
            TabelaCategorias.CAMPOS,
            null,null,null,null,TabelaGatos.CAMPO_NOME
        )

        assert(cursorTodosGatos.count>1)


    }

    private fun insereLivro(bd: SQLiteDatabase, livro: Gato ) {
        livro.id = TabelaGatos(bd).insere(livro.toContentValues())
        assertNotEquals(-1, livro.id)
    }

    private fun insereCategoria(
        bd: SQLiteDatabase,
        categoria: Categoria) {

        categoria.id = TabelaCategorias(bd).insere(categoria.toContentValues())
        assertNotEquals(-1, categoria.id)
    }

    @Test
    fun consegueLerCategorias() {
        val bd = getWritableDatabase()

        val categoriaTux= Categoria("Tuxedo", "Branco/Preto","Médio")
        insereCategoria(bd,categoriaTux)

        val categoriaPersa = Categoria("Persa", "Laranja/Branco", "Médio")
        insereCategoria(bd, categoriaPersa)

        val cursor = TabelaCategorias(bd).consultar(
            TabelaCategorias.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(categoriaTux.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val categBD = Categoria.fromCursor(cursor)

        assertEquals(categoriaTux, categBD)

        val tabelaCategorias = TabelaCategorias(bd)
        val cursorTodasCategorias = tabelaCategorias.consultar(
            TabelaCategorias.CAMPOS,
            null,null,null,null,TabelaCategorias.CAMPO_NOMERACA
        )

        assert(cursorTodasCategorias.count>1)
    }




    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdGatosOpenHelper(getAppContext())
        val bd = openHelper.writableDatabase
        return bd
    }



}