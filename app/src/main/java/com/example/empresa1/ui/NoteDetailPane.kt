package com.example.empresa1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.empresa1.R
import com.example.empresa1.data.Note
import com.example.empresa1.data.Topics
import com.example.empresa1.ui.components.NoteSpinnerRow
import com.example.empresa1.ui.components.RatingInputRow

@Composable
fun NoteDetailPane(
    uiState: NoteUIState,
    modifier: Modifier = Modifier,
    onDetailChange: (NoteDetails) -> Unit
){
    Card(
        modifier = modifier
            .padding(
            horizontal = dimensionResource(id = R.dimen.padding_medium),
            vertical = dimensionResource(id = R.dimen.padding_very_small)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            OutlinedTextField(
                value = uiState.selectedNote?.tittle ?: "",
                onValueChange = {
                    onDetailChange(uiState.noteDetails.copy(tittle = it))
                                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium
            )
            NoteSpinnerRow(
                noteSpinnerPosition = findTopicIndex(uiState.selectedNote?.topic ?: "Otros"),
                onValueChange = {
                    onDetailChange(uiState.noteDetails.copy(topic = Topics.entries[it].name))
                }
            )
            TextField(
                value = uiState.selectedNote?.text ?: "",
                onValueChange = {
                    onDetailChange(uiState.noteDetails.copy(text = it))
                                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge
            )
            RatingInputRow(
                rating = uiState.selectedNote?.rating ?: 0,
                onRatingChange = {rating ->
                    onDetailChange(uiState.noteDetails.copy(rating = rating))
                }
            )
        }
    }
}


@Composable
fun InputRow(
    inputLabel: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Row(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = inputLabel,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .weight(1f)
                .padding(dimensionResource(id = R.dimen.padding_small))
        )
        Box(modifier = Modifier.weight(2f)) {
            content()
        }

    }
}

private fun findTopicIndex(topic: String): Int{
    val noteTopic = Topics.valueOf(topic)
    return Topics.entries.indexOf(noteTopic)
}