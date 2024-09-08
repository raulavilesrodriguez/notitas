package com.example.empresa1.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.empresa1.R

@Composable
fun AddScreen(
    uiState: NoteUIState,
    modifier: Modifier = Modifier,
    onDetailChange: (NoteDetails) -> Unit
){
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        NoteInputForm(
            uiState = uiState,
            onDetailChange = onDetailChange
        )
    }
}


@Composable
fun ButtonRow(
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    submitButtonEnabled: Boolean,
    modifier: Modifier = Modifier
){
    Row(

    ) {

    }
}