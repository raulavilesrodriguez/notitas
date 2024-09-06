package com.example.empresa1.ui

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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteViewModel (
    private val noteRepository: NoteRepository
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(NoteUIState())
    val uiState: StateFlow<NoteUIState> = _uiState.asStateFlow()

    companion object {
        private const val TIMEOUT_MILLS = 5_000L
    }

    fun setSelectedNote(note: Note){
        _uiState.update {
            it.copy(selectedNote = note)
        }
    }

    fun updateName(name: String){
        _uiState.update {
            it.copy(partName = name)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val lookingForNotesUiState : StateFlow<AllNotesUIState> =
        _uiState.flatMapLatest { uiState ->
            noteRepository.lookingForNotesStream(uiState.partName).map {
                AllNotesUIState(it)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
            initialValue = AllNotesUIState()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val favoritesUIState: StateFlow<AllNotesUIState> =
        _uiState.flatMapLatest { uiState ->
            noteRepository.getAllFavoritesStream(uiState.partName).map {
                AllNotesUIState(it)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
            initialValue = AllNotesUIState()
        )

    fun resetInput(){
        _uiState.value = NoteUIState()
    }

    /**
     * to Add notes
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

    suspend fun saveNote(){
        if(validateInput()){
            noteRepository.insertNote(_uiState.value.noteDetails.toNote())
        }
    }

    /**
     *  to UPDATE Notes
     */
    

}

/**
 * Ui State for All Notes Destination
 */
data class AllNotesUIState(
    val notesList : List<Note> = emptyList(),
)

data class NoteUIState(
    val notesList: AllNotesUIState = AllNotesUIState(),
    val selectedNote : Note? = notesList.notesList.firstOrNull(),
    val partName: String = "",
    val noteDetails : NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val tittle: String = "",
    val text: String = "",
    val topic: String = "",
    val favorite: Boolean = false,
    val rating: Int = 0,
    val created: String = getCurrentDateTime()
)

private fun getCurrentDateTime(): String {
    val currentDate = Date()
    val sdf= SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(currentDate)
}

fun NoteDetails.toNote(): Note = Note(
    id = id,
    tittle = tittle,
    text = text,
    topic = topic,
    favorite = favorite,
    rating = rating,
    created = created
)