package pt.ipg.gatos

import android.content.ContentValues

data class Categoria(
    var id: Long = -1,
    var descricao: String) {

    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCategorias.CAMPO_DESCRICAO, descricao)

        return valores
    }
}