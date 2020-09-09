package com.mirogal.cocktail.extension

import java.text.Collator

inline fun <T, R : Comparable<R>> Iterable<T>.sortedByCollator(
        collator: Collator = Collator.getInstance(),
        crossinline selector: (T) -> R?
): List<T> {
    return sortedWith { first, second ->
        collator.compare(selector(first), selector(second))
    }
}