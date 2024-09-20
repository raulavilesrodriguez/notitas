package com.example.empresa1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY created DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT *\n" +
            "  FROM notes\n" +
            " WHERE (tittle LIKE '%' || :partName || '%' OR \n" +
            "        text LIKE '%' || :partName || '%') AND \n" +
            "       favorite = 1\n" +
            " ORDER BY created DESC")
    fun getAllFavorites(partName: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: Int): Flow<Note>

    @Query("SELECT * FROM notes " +
            "WHERE tittle LIKE '%' || :partName || '%' Or text LIKE '%' || :partName || '%' " +
            "ORDER BY created DESC")
    fun lookingForNotes(partName: String): Flow<List<Note>>

}