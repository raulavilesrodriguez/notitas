package com.example.empresa1

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.empresa1.data.Note
import com.example.empresa1.ui.AddScreen
import com.example.empresa1.ui.AppViewModelProvider
import com.example.empresa1.ui.EntryViewModel
import com.example.empresa1.ui.FavoriteViewModel
import com.example.empresa1.ui.HomeScreen
import com.example.empresa1.ui.NameUIState
import com.example.empresa1.ui.NoteDetailPane
import com.example.empresa1.ui.NoteDetails
import com.example.empresa1.ui.NoteUIState
import com.example.empresa1.ui.NoteViewModel
import com.example.empresa1.ui.SearchBar
import com.example.empresa1.ui.emptyScreens.ImageNoNotes
import com.example.empresa1.ui.emptyScreens.NoNotes
import com.example.empresa1.ui.navigation.NoteDestination
import com.example.empresa1.ui.toNoteDetails
import kotlinx.coroutines.launch

private val WINDOW_WIDTH_LARGE = 1200.dp

@Composable
fun NoteApp(
    viewModel: NoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    favoriteViewModel: FavoriteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    entryViewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val nameUiState by viewModel.nameUiState.collectAsStateWithLifecycle()

    val uiStateFavorite by favoriteViewModel.uiState.collectAsStateWithLifecycle()
    val nameUiStateFavorite by favoriteViewModel.nameUiState.collectAsStateWithLifecycle()

    val entryUIState by entryViewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    NavigationWrapperUI(
        uiState = uiState,
        onValueChange = viewModel::updateName,
        onNoteClick = viewModel::setSelectedNote,
        onNoteChange = viewModel::updateNoteDetails,
        nameUIState = nameUiState,
        onUpdateNote = {
            coroutineScope.launch {
                viewModel.updateNote()
            }
        },
        onDelete = {
            coroutineScope.launch {
                viewModel.deleteNote()
            }
        },
        uiStateFavorite = uiStateFavorite,
        onValueChangeFavorite = favoriteViewModel::updateName,
        onNoteClickFavorite = favoriteViewModel::setSelectedNote,
        onNoteChangeFavorite = favoriteViewModel::updateNoteDetails,
        nameUiStateFavorite = nameUiStateFavorite,
        onUpdateFavorite = {
            coroutineScope.launch {
                favoriteViewModel.updateNote()
            }
        },
        onDeleteFavorite = {
            coroutineScope.launch {
                favoriteViewModel.deleteNote()
            }
        },
        onNoteAdd = entryViewModel::updateNoteDetails,
        entryViewModel = entryViewModel,
        entryUIState = entryUIState
    )
}

@Composable
private fun NavigationWrapperUI(
    uiState: NoteUIState,
    onValueChange: (String) -> Unit,
    onNoteClick: (Note) -> Unit,
    onNoteChange: (NoteDetails) -> Unit,
    nameUIState: NameUIState,
    onUpdateNote: () -> Unit,
    onDelete: () -> Unit,
    uiStateFavorite: NoteUIState,
    onValueChangeFavorite: (String) -> Unit,
    onNoteClickFavorite: (Note) -> Unit,
    onNoteChangeFavorite: (NoteDetails) -> Unit,
    nameUiStateFavorite: NameUIState,
    onUpdateFavorite: () -> Unit,
    onDeleteFavorite: () -> Unit,
    onNoteAdd: (NoteDetails) -> Unit,
    entryViewModel: EntryViewModel,
    entryUIState: NoteUIState
){
    var selectedDestination : NoteDestination by remember {
        mutableStateOf(NoteDestination.Notes)
    }

    val windowSize = with(LocalDensity.current) {
        currentWindowSize().toSize().toDpSize()
    }
    val navLayoutType = if (windowSize.width >= WINDOW_WIDTH_LARGE) {
        // Show a permanent drawer when window width is large.
        NavigationSuiteType.NavigationDrawer
    } else {
        // Otherwise use the default from NavigationSuiteScaffold.
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())
    }
    
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NoteDestination.entries.forEach {
                item(
                    label = { Text(text = stringResource(id = it.labelRes))},
                    icon = { Icon(imageVector = it.icon, contentDescription = stringResource(id = it.labelRes))},
                    selected = it == selectedDestination,
                    onClick = {selectedDestination = it}
                )
            }
        },
        layoutType = navLayoutType
    ) {
        when (selectedDestination) {
            NoteDestination.Notes -> NotesDestination(
                uiState = uiState,
                onValueChange = onValueChange,
                onNoteClick = onNoteClick,
                onNoteChange = onNoteChange,
                nameUIState = nameUIState,
                onUpdateNote = onUpdateNote,
                onDelete = onDelete
            )
            NoteDestination.Add -> AddDestination(
                uiState = entryUIState,
                onNoteAdd = onNoteAdd,
                entryViewModel = entryViewModel,
                onNavigateToNotes = {selectedDestination = NoteDestination.Notes}
            )
            NoteDestination.Favorites -> NotesDestination(
                uiState = uiStateFavorite,
                onValueChange = onValueChangeFavorite,
                onNoteClick = onNoteClickFavorite,
                onNoteChange = onNoteChangeFavorite,
                nameUIState = nameUiStateFavorite,
                onUpdateNote = onUpdateFavorite,
                onDelete = onDeleteFavorite
            )
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NotesDestination(
    uiState: NoteUIState,
    onValueChange: (String) -> Unit,
    onNoteClick: (Note) -> Unit,
    onNoteChange: (NoteDetails) -> Unit,
    nameUIState: NameUIState,
    onUpdateNote: () -> Unit,
    onDelete: () -> Unit,
){
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    Log.d("SelectedViewModel", "SELECTED in NoteApp: ${uiState.selectedNote?.toNoteDetails()}")
    Log.d("ViewModel", "NOTEDETAILS in NoteApp: ${uiState.noteDetails}")
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
                   AnimatedPane {
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           SearchBar(
                               value = nameUIState.partName,
                               onValueChange = onValueChange,
                               modifier = Modifier
                                   .padding(
                                       dimensionResource(id = R.dimen.padding_medium)
                                   )
                           )
                           if(uiState.notesList.isNotEmpty()){
                               HomeScreen(
                                   uiState = uiState,
                                   notes = uiState.notesList,
                                   onNoteChange = onNoteChange,
                                   onUpdateNote = onUpdateNote,
                                   onNoteClick = {
                                       onNoteClick(it)
                                       navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, it.id.toLong())
                                   },
                                   onFavoriteClick = {onNoteClick(it)}
                               )
                           } else {
                               NoNotes()
                           }   
                       }
                   }
        },
        detailPane = {
            AnimatedPane {
                if(uiState.selectedNote != null){
                    NoteDetailPane(
                        uiState = uiState,
                        onDetailChange = onNoteChange,
                        onDelete = onDelete,
                        onSubmit = onUpdateNote
                    )
                } else {
                    ImageNoNotes()
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AddDestination(
    uiState: NoteUIState,
    onNoteAdd: (NoteDetails) -> Unit,
    entryViewModel: EntryViewModel,
    onNavigateToNotes: () -> Unit
){
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    val coroutineScope = rememberCoroutineScope()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
                AnimatedPane {
                    AddScreen(
                        uiState = uiState,
                        onDetailChange = onNoteAdd,
                        onCancel = {
                                   coroutineScope.launch {
                                       entryViewModel.resetInput()
                                   }
                            onNavigateToNotes()
                        },
                        onSubmit = {
                            coroutineScope.launch {
                                entryViewModel.saveNote()
                            }
                            onNavigateToNotes()
                        }
                    )
                }
        },
        detailPane = {}
    )
}




