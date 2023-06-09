package pt.ipg.gatos

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns


class TabelaGatos(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {

    override fun criar() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL, $CAMPO_COR TEXT, $CAMPO_GENERO TEXT,$CAMPO_IDADE INTEGER, $CAMPO_PESO FLOAT NOT NULL, $CAMPO_NOMEDONO TEXT, $CAMPO_MORADA TEXT, $CAMPO_PORTE TEXT , $CAMPO_DATA_NASC INTEGER ,$CAMPO_FK_RACA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_RACA) REFERENCES ${TabelaRacas.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun consultar(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    )  : Cursor  {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaRacas.NOME_TABELA} ON ${TabelaRacas.CAMPO_ID} = $CAMPO_FK_RACA"

        return sql.query(db, colunas, selecao, argsSelecao, groupBy, having, orderBy)
    }

    companion object {
        const val NOME_TABELA = "gatos"
        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_COR = "cor"
        const val CAMPO_GENERO = "genero"
        const val CAMPO_DATA_NASC = "data_nascimento"
        const val CAMPO_IDADE = "idade"
        const val CAMPO_PESO = "peso"
        const val CAMPO_NOMEDONO = "dono"
        const val CAMPO_MORADA = "morada"
        const val CAMPO_PORTE = "porte"
        const val CAMPO_FK_RACA = "id_raca"
        const val CAMPO_NOME_RACA = TabelaRacas.CAMPO_NOMERACA
        const val CAMPO_COR_RACA = TabelaRacas.CAMPO_CORPRINCIPALRACA
        const val CAMPO_PORTE_RACA = TabelaRacas.CAMPO_PORTERACA


        //const val CAMPO_TITULO = "titulo"
        //const val CAMPO_ISBN = "isbn"
        //const val CAMPO_DATA_PUB = "data_publicacao"
        // const val CAMPO_FK_CATEGORIA = "id_categoria"

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_COR, CAMPO_GENERO, CAMPO_DATA_NASC,CAMPO_IDADE,CAMPO_PESO, CAMPO_NOMEDONO,CAMPO_MORADA,CAMPO_PORTE ,CAMPO_FK_RACA, CAMPO_NOME_RACA, CAMPO_COR_RACA, CAMPO_PORTE_RACA)
        //val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_TITULO, CAMPO_ISBN, CAMPO_DATA_PUB, CAMPO_FK_CATEGORIA)
    }
}