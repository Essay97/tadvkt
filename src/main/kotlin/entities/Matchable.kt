package entities

interface Matchable {
    val matchers: Set<String>

    fun matches(id: String): Boolean = matchers.contains(id)
}