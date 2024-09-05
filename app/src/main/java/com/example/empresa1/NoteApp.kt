package com.example.empresa1

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.empresa1.data.Note
import com.example.empresa1.ui.AppViewModelProvider
import com.example.empresa1.ui.HomeScreen
import com.example.empresa1.ui.NoteViewModel
import com.example.empresa1.ui.navigation.NoteDestination

private val WINDOW_WIDTH_LARGE = 1200.dp

@Composable
fun NoteApp(
    viewModel: NoteViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val lookingForNotesUiState by viewModel.lookingForNotesUiState.collectAsState()
    val favoritesUiState by viewModel.favoritesUIState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    NavigationWrapperUI(
        notes = lookingForNotesUiState.notesList,
        onValueChange = viewModel::updateName,
        nameValue = uiState.partName,
        onNoteClick = viewModel::setSelectedNote,
        selectedNote = uiState.selectedNote,
        favorites = favoritesUiState.notesList
    )
}

@Composable
private fun NavigationWrapperUI(
    notes: List<Note>,
    onValueChange: (String) -> Unit,
    nameValue: String,
    onNoteClick: (Note) -> Unit,
    selectedNote: Note?,
    favorites: List<Note>,
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
                notes = notes,
                onValueChange = onValueChange,
                nameValue = nameValue,
                onNoteClick = onNoteClick,
                selectedNote = selectedNote
            )
            NoteDestination.Add -> AddDestination()
            NoteDestination.Favorites -> NotesDestination(
                notes = favorites,
                onValueChange = onValueChange,
                nameValue = nameValue,
                onNoteClick = onNoteClick,
                selectedNote = selectedNote
            )
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NotesDestination(
    notes: List<Note>,
    onValueChange: (String) -> Unit,
    nameValue: String,
    onNoteClick: (Note) -> Unit,
    selectedNote: Note?
){
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
                   AnimatedPane {
                       HomeScreen(
                           notes = notes,
                           onValueChange = onValueChange,
                           nameValue = nameValue,
                           onNoteClick = {
                               onNoteClick(it)
                               navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, it.id.toLong())
                           }
                       )
                   }
        },
        detailPane = {
            AnimatedPane {
                if(selectedNote != null){

                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AddDestination(

){
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }


}


