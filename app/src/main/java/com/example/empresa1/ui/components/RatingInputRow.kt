package com.example.empresa1.ui.components

import android.util.Log
import android.widget.RatingBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.empresa1.R
import com.example.empresa1.ui.InputRow
import androidx.compose.runtime.*
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

@Composable
fun RatingInputRow(rating:Int, onRatingChange: (Float) -> Unit, modifier: Modifier = Modifier){
    InputRow(inputLabel = stringResource(R.string.rating), modifier = modifier) {
        RatingBar(
            value = rating.toFloat(),
            style = RatingBarStyle.Fill(),
            onValueChange = onRatingChange,
            onRatingChanged = {
                Log.d("TAG", "onRatingChanged: $it")
            }
        )
    }
}