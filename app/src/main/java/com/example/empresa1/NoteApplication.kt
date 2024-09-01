package com.example.empresa1

import android.app.Application
import com.example.empresa1.data.AppContainer
import com.example.empresa1.data.AppDataContainer

class NoteApplication : Application() {

    lateinit var container : AppContainer

    override fun onCreate(){
        super.onCreate()
        container = AppDataContainer(this)
    }
}