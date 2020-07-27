package com.mirogal.cocktail.util

open class SingletonHolder<out T, in A>(creator: (A) -> T) {

    private var creator: ((A) -> T)? = creator

    @Volatile
    private var _instance: T? = null
    val instance: A.() -> T = {
        _instance ?: synchronized(this@SingletonHolder) {
            _instance ?: let {
                val created = this@SingletonHolder.creator!!(this)
                _instance = created
                this@SingletonHolder.creator = null
                created
            }
        }
    }

}