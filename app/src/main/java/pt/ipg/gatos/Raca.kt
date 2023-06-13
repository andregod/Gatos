package pt.ipg.gatos

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Raca(
    var nomeRaca: String,
    var corPrincipalRaca: String,
    var PorteRaca: String,
    var id: Long = -1
) : Serializable {

    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaRacas.CAMPO_NOMERACA, nomeRaca)
        valores.put(TabelaRacas.CAMPO_CORPRINCIPALRACA, corPrincipalRaca)
        valores.put(TabelaRacas.CAMPO_PORTERACA, PorteRaca)

        return valores
    }


    companion object {
        fun fromCursor(cursor: Cursor): Raca {
            val posID= cursor.getColumnIndex(BaseColumns._ID)
            val posNomeRaca= cursor.getColumnIndex(TabelaRacas.CAMPO_NOMERACA)
            val posCorPrincipalRaca= cursor.getColumnIndex(TabelaRacas.CAMPO_CORPRINCIPALRACA)
            val posPorteRaca= cursor.getColumnIndex(TabelaRacas.CAMPO_PORTERACA)

            val id = cursor.getLong(posID)
            val nomeRaca = cursor.getString(posNomeRaca)
            val corPrincipalRaca = cursor.getString(posCorPrincipalRaca)
            val PorteRaca = cursor.getString(posPorteRaca)

            return Raca(nomeRaca, corPrincipalRaca, PorteRaca,id)
        }

    }
}