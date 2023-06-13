package pt.ipg.gatos

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

abstract class TabelaBD (val db: SQLiteDatabase, val nome: String){

    abstract fun criar()

    fun insere(valores: ContentValues) = db.insert(nome,  null, valores)


    open fun consultar(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    )  : Cursor = db.query(nome, colunas, selecao, argsSelecao, groupBy, having, orderBy)


    fun alterar(valores: ContentValues, where: String, argsWhere: Array<String>) =
    db.update(nome, valores, where, argsWhere)

    fun eliminar(where: String, argsWhere: Array<String>) = db.delete(nome,where,argsWhere)

    companion object {
        const val CHAVE_TABELA = "${ BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT"
    }
}

