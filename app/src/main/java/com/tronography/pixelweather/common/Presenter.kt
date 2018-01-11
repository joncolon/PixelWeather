package com.tronography.pixelweather.common

interface Presenter<V> {
    fun onViewAttached(view: V)

    fun onViewDetached()

    fun onDestroyed()
}