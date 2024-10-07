package com.example.empresa1.ui

import androidx.lifecycle.ViewModel
import com.example.empresa1.data.Note
import com.example.empresa1.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EntryViewModel(
    private val noteRepository: NoteRepository
): ViewModel() {

    // UI state exposed to the data of the NOTE in UI
    private val _uiState = MutableStateFlow(NoteUIState())
    val uiState: StateFlow<NoteUIState> = _uiState.asStateFlow()

    private fun validateInput(uiState: Note? = _uiState.value.selectedNote): Boolean {
        return with(uiState) {
            this!!.tittle.isNotBlank() && text.isNotBlank() && topic.isNotBlank()
        }
    }
    fun updateNoteDetails(selectedNote: Note){
        _uiState.update {
            it.copy(selectedNote = selectedNote, isEntryValid = validateInput(selectedNote))
        }
    }

    /**
     * To ADD Notes
     */
    suspend fun saveNote(){
        if(validateInput()){
            noteRepository.insertNote(_uiState.value.selectedNote?:Note())
        }
        resetInput()
    }

    fun resetInput(){
        _uiState.update {
            it.copy(
                selectedNote = Note()
            )
        }
    }
}