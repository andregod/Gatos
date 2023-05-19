package pt.ipg.gatos

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Raca(
    var nomeRaca: String,
    var corPrincipalRaca: String,
    var PorteRaca: String,
    var id: Long = -1
) {

    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCategorias.CAMPO_NOMERACA, nomeRaca)
        valores.put(TabelaCategorias.CAMPO_CORPRINCIPALRACA, corPrincipalRaca)
        valores.put(TabelaCategorias.CAMPO_PORTERACA, PorteRaca)

        return valores
    }


    companion object {
        fun fromCursor(cursor: Cursor): Raca {
            val posID= cursor.getColumnIndex(BaseColumns._ID)
            val posNomeRaca= cursor.getColumnIndex(TabelaCategorias.CAMPO_NOMERACA)
            val posCorPrincipalRaca= cursor.getColumnIndex(TabelaCategorias.CAMPO_CORPRINCIPALRACA)
            val posPorteRaca= cursor.getColumnIndex(TabelaCategorias.CAMPO_PORTERACA)

            val id = cursor.getLong(posID)
            val nomeRaca = cursor.getString(posNomeRaca)
            val corPrincipalRaca = cursor.getString(posCorPrincipalRaca)
            val PorteRaca = cursor.getString(posPorteRaca)

            return Raca(nomeRaca, corPrincipalRaca, PorteRaca,id)
        }

    }
}