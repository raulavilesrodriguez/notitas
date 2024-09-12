package com.example.empresa1.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.empresa1.R
import com.example.empresa1.data.Note
import com.example.empresa1.data.Topics
import com.example.empresa1.ui.components.NoteSpinnerRow
import com.example.empresa1.ui.components.RatingInputRow
import com.example.empresa1.ui.theme.Empresa1Theme

@Composable
fun NoteDetailPane(
    uiState: NoteUIState,
    modifier: Modifier = Modifier,
    onDetailChange: (NoteDetails) -> Unit
){
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        NoteInputForm(
            noteDetails = uiState.noteDetails,
            onDetailChange = onDetailChange
        )
        Spacer(modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .width(dimensionResource(id = R.dimen.padding_small)))
        HorizontalDivider()
        if(uiState.isEntryValid){
            MessageInput(
                message = R.string.correct_update,
                avatar = R.drawable.happy,
                color = Color.Green
            )
        } else {
            MessageInput(
                message = R.string.incorrect_update,
                avatar = R.drawable.unhappy,
                color = Color.Red
            )
        }
    }
}

@Composable
fun NoteInputForm(
    noteDetails: NoteDetails,
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
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            TittleInputRow(
                noteDetails = noteDetails,
                onDetailChange = onDetailChange
            )
            NoteSpinnerRow(
                noteSpinnerPosition = findTopicIndex(noteDetails.topic),
                onValueChange = {
                    onDetailChange(noteDetails.copy(topic = Topics.entries[it].name))
                }
            )
            TextField(
                value = noteDetails.text,
                onValueChange = {
                    onDetailChange(noteDetails.copy(text = it))
                                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(dimensionResource(id = R.dimen.height_body_note)),
                singleLine = false,
                textStyle = MaterialTheme.typography.bodyLarge,
                label = { Text(text = stringResource(id = R.string.label_note))}
            )
            RatingInputRow(
                rating = noteDetails.rating,
                onRatingChange = {rating ->
                    onDetailChange(noteDetails.copy(rating = rating))
                }
            )
        }
    }
}

@Composable
fun TittleInputRow(
    noteDetails: NoteDetails,
    modifier: Modifier = Modifier,
    onDetailChange: (NoteDetails) -> Unit
){
    InputRow(inputLabel = stringResource(id = R.string.tittle), modifier = modifier) {
        OutlinedTextField(
            value = noteDetails.tittle,
            onValueChange = {
                onDetailChange(noteDetails.copy(tittle = it))
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
        Box(modifier = Modifier.weight(2.5f)) {
            content()
        }

    }
}

private fun findTopicIndex(topic: String): Int{
    val noteTopic = Topics.valueOf(topic)
    return Topics.entries.indexOf(noteTopic)
}

@Composable
private fun MessageInput(
    message: Int,
    avatar: Int,
    color: Color,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.padding_2))
    ){
        Row(
            modifier = modifier
        ) {
            Text(
                text = stringResource(id = message),
                color = color,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
            Image(
                painter = painterResource(id = avatar),
                contentDescription = null,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteDetailPanePreview(){
    Empresa1Theme {
        NoteInputForm(
            noteDetails = NoteDetails(),
            onDetailChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageInputPreview(){
    Empresa1Theme {
        MessageInput(
            message = R.string.incorrect_update,
            avatar = R.drawable.unhappy,
            color = Color.Red
        )
    }
}