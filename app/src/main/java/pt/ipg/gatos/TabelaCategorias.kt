package pt.ipg.gatos

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns



class TabelaCategorias(db: SQLiteDatabase) : TabelaBD(db, "Categorias") {
    override fun criar() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOMERACA TEXT NOT NULL, $CAMPO_CORPRINCIPALRACA TEXT NOT NULL, $CAMPO_PORTERACA TEXT NOT NULL)")
    }

    companion object {
        const val NOME_TABELA = "categorias"
        const val CAMPO_NOMERACA = "nome_raca"
        const val CAMPO_CORPRINCIPALRACA = "cor_principal_raca"
        const val CAMPO_PORTERACA = "porte_raca"
        //const val CAMPO_DESCRICAO = "descricao"

        //val CAMPOS = arrayOf (BaseColumns._ID, CAMPO_DESCRICAO)
        val CAMPOS = arrayOf (BaseColumns._ID, CAMPO_NOMERACA, CAMPO_CORPRINCIPALRACA, CAMPO_PORTERACA)

    }
}