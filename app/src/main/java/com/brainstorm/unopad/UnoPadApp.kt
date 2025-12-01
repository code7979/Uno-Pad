package com.brainstorm.unopad

import android.app.Application
import com.brainstorm.unopad.di.AppContainer

class UnoPadApp : Application() {
    // AppContainer is automatically a singleton because Application is singleton
    val appContainer by lazy { AppContainer(this) }
}
