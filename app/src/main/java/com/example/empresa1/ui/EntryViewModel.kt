package com.example.empresa1.ui

import androidx.lifecycle.ViewModel
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
     * To ADD Notes
     */
    suspend fun saveNote(){
        if(validateInput()){
            noteRepository.insertNote(_uiState.value.noteDetails.toNote())
            resetInput()
        }
    }

    fun resetInput(){
        _uiState.value = NoteUIState()
    }
}