package com.obedcodes.saturn

import android.app.Application
import com.google.firebase.FirebaseApp

class SaturnApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Fuck me, this code kept for long... Initialize Fire-B
        FirebaseApp.initializeApp(this)
    }
}
