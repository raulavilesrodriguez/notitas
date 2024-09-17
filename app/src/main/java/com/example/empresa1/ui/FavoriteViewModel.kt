package com.example.empresa1.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empresa1.data.Note
import com.example.empresa1.data.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel (
    private val noteRepository: NoteRepository
) : ViewModel() {

    // UI state exposed to the data of the NOTE in UI
    private val _uiState = MutableStateFlow(NoteUIState())
    val uiState: StateFlow<NoteUIState> = _uiState.asStateFlow()

    // UI state to SEARCH NOTES
    private val _nameUiState = MutableStateFlow(NameUIState())
    val nameUiState: StateFlow<NameUIState> = _nameUiState.asStateFlow()

    fun setSelectedNote(note: Note){
        _uiState.update {
            it.copy(
                selectedNote = note,
                isEntryValid = true
            )
        }
    }

    fun updateName(name: String){
        _nameUiState.update {
            it.copy(partName = name)
        }
        observeNotes()
    }

    init {
        observeNotes()
    }

    private fun observeNotes(){
        viewModelScope.launch {
            noteRepository.getAllFavoritesStream(_nameUiState.value.partName).collect{
                _uiState.value = NoteUIState(
                    notesList = it,
                    selectedNote = it.firstOrNull(),
                    isEntryValid = validateInput(it.firstOrNull()?.toNoteDetails() ?: NoteDetails())
                )
            }
        }
    }

    /**
     * to Update notes
     */
    private fun validateInput(uiState: NoteDetails = _uiState.value.noteDetails): Boolean {
        return with(uiState) {
            tittle.isNotBlank() && text.isNotBlank() && topic.isNotBlank()
        }
    }

    fun updateNoteDetails(noteDetails: NoteDetails){
        _uiState.update {
            it.copy(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
        }
    }

    /**
     *  to UPDATE Notes
     */
    suspend fun updateNote(){
        if(validateInput(_uiState.value.noteDetails)){
            noteRepository.updateNote(_uiState.value.noteDetails.toNote())
        }
    }

    /**
     * to DELETE Notes
     */
    suspend fun deleteNote(){
        noteRepository.deleteNote(_uiState.value.noteDetails.toNote())
    }
}