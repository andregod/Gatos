package pt.ipg.gatos

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Categoria(
    var descricao: String,
    var id: Long = -1
) {

    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCategorias.CAMPO_DESCRICAO, descricao)

        return valores
    }


    companion object {
        fun fromCursor(cursor: Cursor): Categoria {
            val posID= cursor.getColumnIndex(BaseColumns._ID)
            val posDescricao= cursor.getColumnIndex(TabelaCategorias.CAMPO_DESCRICAO)
            val id = cursor.getLong(posID)
            val descricao = cursor.getString(posDescricao)

            return Categoria(descricao,id)
        }

    }
}