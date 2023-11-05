package common

typealias List2D<T> = List<List<T>>

object Lists2D {

    fun <T> iterateUntilStable(map2D: List2D<T>, update: (List2D<T>) -> List2D<T>): List2D<T> {
        fun updateWithChange(map2D: List2D<T>): Pair<Boolean, List2D<T>> {
            val next = update(map2D)
            return (map2D == next) to next
        }
        return iterateUntilNoChange(map2D, ::updateWithChange)
    }

    fun <T> iterateUntilNoChange(map2D: List2D<T>, update: (List2D<T>) -> Pair<Boolean, List2D<T>>): List2D<T> {
        var last: List2D<T> = map2D
        do {
            val (changed, next) = update(last)
            if (!changed) return next
            last = next
        } while (true)
    }


    fun <T> List2D<T>.indices2d() =
        this.indices.flatMap { i -> this[i].indices.map { j -> i to j } }

    fun <T> Array<Array<T>>.indices2d() =
        this.indices.flatMap { i -> this[i].indices.map { j -> i to j } }

    fun <T> List2D<T>.getOrNull(ij: Pair<Int, Int>) = this.getOrNull(ij.first)?.getOrNull(ij.second)
    fun Pair<Int, Int>.neighbours(): List<Pair<Int, Int>> = listOf(
        (first + 1) to second,
        (first - 1) to second,
        first to (second + 1),
        first to (second - 1)
    )

    fun Pair<Int, Int>.allNeighbour(): List<Pair<Int, Int>> {
        val (i, j) = this
        return listOf(
            (i + 1 to j + 1),
            (i + 1 to j),
            (i + 1 to j - 1),
            (i to j + 1),
            (i to j - 1),
            (i - 1 to j - 1),
            (i - 1 to j),
            (i - 1 to j + 1)
        )
    }

    operator fun Pair<Int, Int>.plus(pair: Pair<Int, Int>) = (first + pair.first) to (second + pair.second)
    operator fun Pair<Int, Int>.minus(pair: Pair<Int, Int>) = (first - pair.first) to (second - pair.second)
    operator fun <T> List2D<T>.get(ij: Pair<Int, Int>): T = this[ij.first][ij.second]
    fun <T, V> List2D<T>.map2DIndexed(transform: (Pair<Int, Int>, T) -> V): List2D<V> {
        return this.mapIndexed { i, ts ->
            ts.mapIndexed { j, t ->
                transform(i to j, t)
            }
        }
    }

    fun <T, V> List2D<T>.map2D(transform: (T) -> V): List2D<V> {
        return this.map {
            it.map(transform)
        }
    }
}