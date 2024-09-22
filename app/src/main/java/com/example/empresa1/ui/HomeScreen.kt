package com.example.empresa1.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.empresa1.R
import com.example.empresa1.data.Avatar
import com.example.empresa1.data.LocalAvatarsData
import com.example.empresa1.data.Note
import com.example.empresa1.ui.theme.Empresa1Theme



@Composable
fun HomeScreen(
    uiState: NoteUIState,
    notes: List<Note>,
    onNoteChange: (NoteDetails) -> Unit,
    onUpdateNote: () -> Unit,
    onNoteClick: (Note) -> Unit,
    onFavoriteClick: (Note) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        NotesList(
            uiState = uiState,
            notes = notes,
            onNoteChange = onNoteChange,
            onUpdateNote = onUpdateNote,
            onNoteClick = onNoteClick,
            onFavoriteClick = onFavoriteClick
        )
    }
}


@Composable
private fun NotesList(
    uiState: NoteUIState,
    notes: List<Note>,
    onNoteChange: (NoteDetails) -> Unit,
    onUpdateNote: () -> Unit,
    onNoteClick: (Note) -> Unit,
    onFavoriteClick: (Note) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(items = notes, key = {it.id}){
            val avatar = LocalAvatarsData.avatars.firstOrNull { avatar ->
                stringResource(id = avatar.description) == it.topic} ?: LocalAvatarsData.avatars.first()
            NoteCard(
                noteDetails = uiState.noteDetails,
                note = it,
                onNoteChange = onNoteChange,
                onUpdateNote = onUpdateNote,
                onFavoriteClick = onFavoriteClick,
                avatar = avatar,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onNoteClick(it) }
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
){
    TextField(
        value = value,
        onValueChange = {onValueChange(it)},
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        placeholder = {
            Text(text = stringResource(id = R.string.notes_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(dimensionResource(id = R.dimen.height_search)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium))
    )
}

@Composable
private fun NoteCard(
    noteDetails: NoteDetails,
    note: Note,
    onNoteChange: (NoteDetails) -> Unit,
    onUpdateNote: () -> Unit,
    onFavoriteClick: (Note) -> Unit,
    avatar: Avatar,
    modifier: Modifier = Modifier
){
    var addOrDeleteFavorites by rememberSaveable { mutableStateOf(note.favorite) }
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                NoteImage(drawableResource = avatar.imageId, description = avatar.description)
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_very_small)))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = note.topic,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = note.tittle,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.padding_small))
                    )
                }
                if(addOrDeleteFavorites){
                    Log.d("SelectedNOTEE", "SELECTED in HomeScreen: $note")
                    IconButton(
                        onClick = {
                            onNoteChange(noteDetails.copy(
                                id = note.id,
                                tittle = note.tittle,
                                text = note.text,
                                topic = note.topic,
                                favorite = false,
                                rating = note.rating,
                                created = note.created ?: ""
                            ))
                            onUpdateNote()
                            onFavoriteClick(note)
                            addOrDeleteFavorites = false
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.star_filled),
                            contentDescription = stringResource(R.string.favorite_notes)
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            onNoteChange(noteDetails.copy(
                                id = note.id,
                                tittle = note.tittle,
                                text = note.text,
                                topic = note.topic,
                                favorite = true,
                                rating = note.rating,
                                created = note.created ?: ""
                            ))
                            onUpdateNote()
                            onFavoriteClick(note)
                            addOrDeleteFavorites = true
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.star_border),
                            contentDescription = stringResource(R.string.favorite_notes)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteImage(
    drawableResource: Int,
    description: Int,
    modifier: Modifier = Modifier,
){
    Image(
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape),
        painter = painterResource(id = drawableResource),
        contentDescription = stringResource(id = description)
    )
}


@Preview(showBackground = true)
@Composable
private fun NoteCardPreview(){
    Empresa1Theme{
        NoteCard(
            noteDetails = NoteDetails(),
            note = Note(0, "Info Bco Pichincha", "Clave:1234, gbh@gmai.com", "Finanzas"),
            onNoteChange = {},
            onUpdateNote = {},
            onFavoriteClick = {},
            avatar = Avatar(R.drawable.`fun`, R.string.`fun`))
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview(){
    Empresa1Theme {
        SearchBar()
    }
}