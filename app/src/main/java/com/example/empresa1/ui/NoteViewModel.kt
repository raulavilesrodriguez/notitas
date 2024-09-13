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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteViewModel (
    private val noteRepository: NoteRepository
) : ViewModel() {

    // UI state exposed to the data of the NOTE in UI
    private val _uiState = MutableStateFlow(NoteUIState())
    val uiState: StateFlow<NoteUIState> = _uiState.asStateFlow()

    // UI state to SEARCH NOTES
    private val _nameUiState = MutableStateFlow(NameUIState())
    val nameUiState: StateFlow<NameUIState> = _nameUiState.asStateFlow()

    // UI state of SELECTED NOTE
    private val _selectedNoteUI = MutableStateFlow(PaneUIState())
    val selectNoteUIState: StateFlow<PaneUIState> = _selectedNoteUI.asStateFlow()

    companion object {
        private const val TIMEOUT_MILLS = 5_000L
    }

    fun setSelectedNote(note: Note){
        _selectedNoteUI.update {
            it.copy(
                selectedNote = note
            )
        }
        _uiState.update {
            it.copy(noteDetails = note.toNoteDetails(), isEntryValid = true)
        }
    }

    fun updateName(name: String){
        _nameUiState.update {
            it.copy(partName = name)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val lookingForNotesUiState : StateFlow<PaneUIState> =
        _nameUiState.flatMapLatest { nameUiState ->
            noteRepository.lookingForNotesStream(nameUiState.partName).map {
                val currentSelected = _selectedNoteUI.value.selectedNote
                PaneUIState(notesList = it, selectedNote = currentSelected ?: it.firstOrNull())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
            initialValue = PaneUIState()
        )

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

/**
 * Ui State for All Notes Destination
 */
data class PaneUIState(
    val notesList: List<Note> = emptyList(),
    val selectedNote : Note? = null,
)

/**
 * Data class to search notes
 */
data class NameUIState(
    val partName: String = "",
)

/**
 * Data class to show note details
 */
data class NoteUIState(
    val noteDetails : NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val tittle: String = "",
    val text: String = "",
    val topic: String = "Otros",
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

fun Note.toNoteDetails(): NoteDetails = NoteDetails(
    id = id,
    tittle = tittle,
    text = text,
    topic = topic,
    favorite = favorite,
    rating = rating,
    created = created?: ""
)