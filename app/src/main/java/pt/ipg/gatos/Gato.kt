package pt.ipg.gatos

import android.content.ContentValues

data class Gato(
    var titulo: String,
    var idCategoria: Int,
    var isbn: String? = null,
    var id: Long = -1
) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaGatos.CAMPO_TITULO, titulo)
        valores.put(TabelaGatos.CAMPO_ISBN, isbn)
        valores.put(TabelaGatos.CAMPO_FK_CATEGORIA, idCategoria)

        return valores
    }

}