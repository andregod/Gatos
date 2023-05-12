package pt.ipg.gatos

data class Gato(
    var titulo: String,
    var idCategoria: Int,
    var isbn: String? = null,
    var id: Long = -1
) {


}