package com.example.empresa1

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import com.example.empresa1.data.Note
import com.example.empresa1.ui.HomeScreen


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NoteAppContent(
    notes: List<Note>,
    onValueChange: (String) -> Unit,
    onNoteClick: (Note) -> Unit,
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
                           onNoteClick = {
                               onNoteClick(it)
                               navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, it.id.toLong())
                           }
                       )
                   }
        },
        detailPane = {
            AnimatedPane {

            }
        }
    )
}