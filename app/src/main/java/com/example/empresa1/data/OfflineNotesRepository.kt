package com.example.empresa1.data

import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val noteDao: NoteDao): NoteRepository {
    override suspend fun insertNote(note: Note) = noteDao.insert(note)

    override suspend fun updateNote(note: Note) = noteDao.update(note)

    override suspend fun deleteNote(note: Note) = noteDao.delete(note)

    override fun getAllNotesStream(): Flow<List<Note>> = noteDao.getAllNotes()

    override fun getAllFavoritesStream(): Flow<List<Note>> = noteDao.getAllFavorites()

    override fun getNoteStream(id: Int): Flow<Note> = noteDao.getNote(id)
}