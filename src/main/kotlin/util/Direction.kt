package util

enum class Direction {
    N, S, W, E;

    fun opposite(): Direction {
        return when (this) {
            N -> S
            S -> N
            W -> E
            E -> W
        }
    }
}