package com.tronography.pixelweather.common

import android.content.Context
import android.support.v4.content.Loader
import android.util.Log

internal class PresenterLoader<T : Presenter<*>>(context: Context, private val factory: PresenterFactory<T>, private val tag: String) : Loader<T>(context) {
    var presenter: T? = null
        private set

    override fun onStartLoading() {
        Log.i("loader", "onStartLoading-" + tag)

        // if we already own a presenter instance, simply deliver it.
        if (presenter != null) {
            deliverResult(presenter)
            return
        }

        // Otherwise, force a load
        forceLoad()
    }

    override fun onForceLoad() {
        Log.i("loader", "onForceLoad-" + tag)

        // Create the Presenter using the Factory
        presenter = factory.create()

        // Deliver the result
        deliverResult(presenter)
    }

    override fun deliverResult(data: T?) {
        super.deliverResult(data)
        Log.i("loader", "deliverResult-" + tag)
    }

    override fun onStopLoading() {
        Log.i("loader", "onStopLoading: " + tag)
    }

    override fun onReset() {
        Log.i("loader", "onReset-" + tag)
        if (presenter != null) {
            presenter?.onDestroyed()
            presenter = null
        }
    }
}