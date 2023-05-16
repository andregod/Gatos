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
        val categoria = Categoria("Humor")
        val id = insereCategoria(bd, categoria)
        assertNotEquals(-1, id )


    }

    @Test
    fun consegueInserirLivros() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Humor")
        insereCategoria(bd, categoria)

        val livro = Gato("O lixo na minha cabeça", categoria.id)
        insereLivro(bd,livro)

        val livro2 = Gato("Novissimas cronicas da boca do inferno",categoria.id)
        insereLivro(bd, livro2)
    }

    @Test
    fun consegueLerLivros() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Contos")
        insereCategoria(bd, categoria)

        val livro1 = Gato("Todos os contos", categoria.id)
        insereLivro(bd,livro1)

        val dataPub = Calendar.getInstance()
        dataPub.set(2016, 4, 1)

        val livro2 = Gato("Cinderela",categoria.id,"978-1473683556", dataPub)
        insereLivro(bd, livro2)

        val tabelaLivros = TabelaGatos(bd)
        val cursor = tabelaLivros.consultar(
            TabelaGatos.CAMPOS,
            null,null,null,null,TabelaCategorias.CAMPO_DESCRICAO
        )

        val livroBD = Gato.fromCursor(cursor)

        assertEquals(livro1, livroBD)

        val cursorTodosLivros = tabelaLivros.consultar(
            TabelaCategorias.CAMPOS,
            null,null,null,null,TabelaGatos.CAMPO_TITULO
        )

        assert(cursorTodosLivros.count>1)


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

        val categoriaRom = Categoria("Romance")
        insereCategoria(bd,categoriaRom)

        val categoriaFic = Categoria("Ficção Cientifica")
        insereCategoria(bd, categoriaFic)

        val cursor = TabelaCategorias(bd).consultar(
            TabelaCategorias.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(categoriaFic.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val categBD = Categoria.fromCursor(cursor)

        assertEquals(categoriaFic, categBD)

        val tabelaCategorias = TabelaCategorias(bd)
        val cursorTodasCategorias = tabelaCategorias.consultar(
            TabelaCategorias.CAMPOS,
            null,null,null,null,TabelaCategorias.CAMPO_DESCRICAO
        )

        assert(cursorTodasCategorias.count>1)
    }




    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdGatosOpenHelper(getAppContext())
        val bd = openHelper.writableDatabase
        return bd
    }



}