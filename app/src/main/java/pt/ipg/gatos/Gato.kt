package pt.ipg.gatos

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Gato(
    var nome: String,
    var cor: String,
    var dataNascimento: Calendar? = null,
    var idade: Int,
    var peso: Float,
    var nomeDono: String,
    var morada: String,
    var porteGato: String,
    var idCategoria: Long,

    var id: Long = -1
) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaGatos.CAMPO_NOME, nome)
        valores.put(TabelaGatos.CAMPO_COR, cor)
        valores.put(TabelaGatos.CAMPO_DATA_NASC, dataNascimento?.timeInMillis)
        valores.put(TabelaGatos.CAMPO_IDADE, idade)
        valores.put(TabelaGatos.CAMPO_PESO, peso)
        valores.put(TabelaGatos.CAMPO_NOMEDONO, nomeDono)
        valores.put(TabelaGatos.CAMPO_MORADA, morada)
        valores.put(TabelaGatos.CAMPO_PORTE, porteGato)
        valores.put(TabelaGatos.CAMPO_FK_RACA, idCategoria)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Gato {
            val posID = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaGatos.CAMPO_NOME)
            val posCor = cursor.getColumnIndex(TabelaGatos.CAMPO_COR)
            val posDataNasc = cursor.getColumnIndex(TabelaGatos.CAMPO_DATA_NASC)
            val posIdade = cursor.getColumnIndex(TabelaGatos.CAMPO_IDADE)
            val posPeso = cursor.getColumnIndex(TabelaGatos.CAMPO_PESO)
            val posNomeDono = cursor.getColumnIndex(TabelaGatos.CAMPO_NOMEDONO)
            val posMorada = cursor.getColumnIndex(TabelaGatos.CAMPO_MORADA)
            val posPorteGato = cursor.getColumnIndex(TabelaGatos.CAMPO_PORTE)
            val posCategoriaFK = cursor.getColumnIndex(TabelaGatos.CAMPO_FK_RACA)

            val id = cursor.getLong(posID)
            val nome = cursor.getString(posNome)
            val cor= cursor.getString(posCor)

            var dataNascimento: Calendar?
            if (cursor.isNull(posDataNasc)) {
                dataNascimento = null
            } else {
                dataNascimento = Calendar.getInstance()
                dataNascimento.timeInMillis = cursor.getLong((posDataNasc))
            }

            val idade = cursor.getInt(posIdade)
            val peso = cursor.getFloat(posPeso)
            val nomeDono = cursor.getString(posNomeDono)
            val morada = cursor.getString(posMorada)
            val porteGato = cursor.getString(posPorteGato)


            val idCategoria = cursor.getLong(posCategoriaFK)


            return Gato(nome, cor, dataNascimento, idade, peso, nomeDono, morada, porteGato, idCategoria, id)
        }
    }

}