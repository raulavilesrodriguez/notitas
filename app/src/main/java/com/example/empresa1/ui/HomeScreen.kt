package com.example.empresa1.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.empresa1.data.Note
import com.example.empresa1.ui.theme.Empresa1Theme

@Composable
fun NotesList(

    modifier: Modifier = Modifier
){

}

@Composable
fun NoteCard(
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
            modifier = Modifier.width(255.dp)
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