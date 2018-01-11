package com.tronography.pixelweather.common

/**
 * Creates a Presenter object.
 * @param <T> presenter type
</T> */
interface PresenterFactory<T : Presenter<*>> {
    fun create(): T
}