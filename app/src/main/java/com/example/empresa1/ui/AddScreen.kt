package com.example.empresa1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.empresa1.R
import com.example.empresa1.ui.theme.Empresa1Theme
import java.util.Locale

@Composable
fun AddScreen(
    uiState: NoteUIState,
    modifier: Modifier = Modifier,
    onDetailChange: (NoteDetails) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit
){
    Column(
        modifier = modifier
    ) {
        NoteInputForm(
            noteDetails = uiState.noteDetails,
            onDetailChange = onDetailChange
        )
        ButtonRow(
            onCancel = onCancel,
            onSubmit = onSubmit,
            submitButtonEnabled = uiState.isEntryValid,
            descriptionButtonLeft = stringResource(R.string.cancel)
        )
    }
}


@Composable
fun ButtonRow(
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    submitButtonEnabled: Boolean,
    descriptionButtonLeft: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_very_small))
    ) {
        OutlinedButton(
            onClick = onCancel,
            border = null,
            modifier = Modifier.weight(1f),
        ) {
            Text(text = descriptionButtonLeft.uppercase(Locale.getDefault()))
        }
        Button(
            onClick = onSubmit,
            enabled = submitButtonEnabled,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(id = R.string.save).uppercase(Locale.getDefault()))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddScreenPreview(){
    Empresa1Theme {
        AddScreen(
            uiState = NoteUIState(),
            onDetailChange = {},
            onCancel = { },
            onSubmit = {}
            )
    }
}
