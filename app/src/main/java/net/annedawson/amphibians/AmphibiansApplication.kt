package net.annedawson.amphibians

import android.app.Application
import net.annedawson.amphibians.data.AppContainer
import net.annedawson.amphibians.data.DefaultAppContainer

class AmphibiansApplication : Application(){

    // The Application() class is used to initialize
    // and provide a dependency injection container
    // to the rest of the app.
    // The Application class is the base class
    // for all Android applications.
    // It is used to initialize the application
    // and to provide global resources to the rest of the app.

    // NOTE: AmphibiansApplication is the
    // Application name in the manifest file

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }



}