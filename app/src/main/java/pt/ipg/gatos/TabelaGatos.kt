package pt.ipg.gatos

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaGatos(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    companion object {
        const val NOME_TABELA = "Gatos"
    }

    override fun criar() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, titulo TEXT NOT NULL, isbn TEXT, id_categoria INTEGER NOT NULL, FOREIGN KEY (id_categoria) REFERENCES ${TabelaCategorias.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }
}