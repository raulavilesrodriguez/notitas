package com.example.empresa1.ui.emptyScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.empresa1.R

@Composable
fun NoNotes() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.no_notes),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}