package pt.ipg.gatos

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns



class TabelaCategorias(db: SQLiteDatabase) : TabelaBD(db, "Categorias") {
    override fun criar() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, descricao TEXT NOT NULL)")
    }

    companion object {
        const val NOME_TABELA = "categorias"

        const val CAMPO_DESCRICAO = "descricao"
    }
}