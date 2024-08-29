package com.example.empresa1.ui

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.empresa1.R
import com.example.empresa1.data.Avatar
import com.example.empresa1.data.LocalAvatarsData
import com.example.empresa1.data.Note
import com.example.empresa1.ui.theme.Empresa1Theme



@Composable
fun HomeScreen(
    notes: List<Note>,
    onValueChange: (String) -> Unit,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if(notes.isEmpty()){
            Text(
                text = stringResource(id = R.string.no_notes),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        } else {
            NotesList(
                notes = notes,
                onValueChange = onValueChange,
                onNoteClick = onNoteClick,
            )
        }
    }
}


@Composable
private fun NotesList(
    notes: List<Note>,
    onValueChange: (String) -> Unit,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            SearchBar(
                onValueChange = onValueChange,
                modifier = Modifier
            )
        }
        items(items = notes, key = {it.id}){
            val avatar = LocalAvatarsData.avatars.firstOrNull { avatar ->
                stringResource(id = avatar.description) == it.topic} ?: LocalAvatarsData.avatars.first()
            NoteCard(
                note = it,
                avatar = avatar,
                modifier = Modifier
                    .clickable { onNoteClick(it) }
            )
        }
    }
}

@Composable
private fun SearchBar(
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
    note: Note,
    avatar: Avatar,
    modifier: Modifier = Modifier
){
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(dimensionResource(id = R.dimen.width_note))
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
            note = Note(0, "Info Bco Pichincha", "Clave:1234, gbh@gmai.com", "Finanzas"),
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