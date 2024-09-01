package com.example.empresa1.data

import android.content.Context

interface AppContainer {
    val noteRepository : NoteRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val noteRepository: NoteRepository by lazy {
        OfflineNotesRepository(NoteDatabase.getDatabase(context).noteDao())
    }
}