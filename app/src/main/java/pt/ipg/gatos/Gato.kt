package pt.ipg.gatos

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Gato(
    var titulo: String,
    var idCategoria: Long,
    var isbn: String? = null,
    var dataPublicacao: Calendar? = null,
    var id: Long = -1
) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaGatos.CAMPO_TITULO, titulo)
        valores.put(TabelaGatos.CAMPO_ISBN, isbn)
        valores.put(TabelaGatos.CAMPO_DATA_PUB, dataPublicacao?.time)
        valores.put(TabelaGatos.CAMPO_FK_CATEGORIA, idCategoria)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Gato {
            val posID = cursor.getColumnIndex(BaseColumns._ID)
            val posTitulo = cursor.getColumnIndex(TabelaGatos.CAMPO_TITULO)
            val posISBN = cursor.getColumnIndex(TabelaGatos.CAMPO_ISBN)
            val posDataPub = cursor.getColumnIndex(TabelaGatos.CAMPO_DATA_PUB)
            val posCategoriaFK = cursor.getColumnIndex(TabelaGatos.CAMPO_FK_CATEGORIA)


            val id = cursor.getLong(posID)
            val titulo = cursor.getString(posTitulo)
            val isbn = cursor.getString(posISBN)

            var dataPub: Calendar?
            if (cursor.isNull(posDataPub)) {
                dataPub = null
            } else {
                dataPub = Calendar.getInstance()
                dataPub.timeInMillis = cursor.getLong((posDataPub))
            }

            val categoriaId = cursor.getLong(posCategoriaFK)


            return Gato(titulo, categoriaId, isbn, dataPub, id)
        }
    }

}