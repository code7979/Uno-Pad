package com.brainstorm.unopad

import android.util.Log

const val LOGGER_TAG = "UnoPadLogger"

@Suppress("NOTHING_TO_INLINE")
inline fun <T> log(klass: Class<T>, message: String) {
    Log.d(LOGGER_TAG, "${klass.name}: $message")
}
