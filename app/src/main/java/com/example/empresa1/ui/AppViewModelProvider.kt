package com.example.empresa1.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.empresa1.NoteApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            NoteViewModel(
                noteApplication().container.noteRepository
            )
        }
        initializer {
            EntryViewModel(
                noteApplication().container.noteRepository
            )
        }
        initializer {
            FavoriteViewModel(
                noteApplication().container.noteRepository
            )
        }
    }
}


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [NoteApplication].
 */
fun CreationExtras.noteApplication(): NoteApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteApplication)