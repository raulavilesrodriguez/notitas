package com.example.empresa1.ui.components

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.empresa1.R
import com.example.empresa1.data.Topics
import com.example.empresa1.ui.InputRow

class SpinnerAdapter(val onValueChange: (Int) -> Unit): AdapterView.OnItemSelectedListener{
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onValueChange(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onValueChange(0)
    }
}

@Composable
fun NoteSpinnerRow(
    noteSpinnerPosition: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val noteTopicArray = Topics.entries.map {
        stringResource(id = it.label)
    }
    InputRow(inputLabel = stringResource(id = R.string.Topics), modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {context ->
                Spinner(context).apply {
                    adapter =
                        ArrayAdapter(
                            context,
                            android.R.layout.simple_spinner_dropdown_item,
                            noteTopicArray
                        )
                }
            },
            update = {spinner ->
                spinner.setSelection(noteSpinnerPosition)
                spinner.onItemSelectedListener = SpinnerAdapter(onValueChange)
            }
        )
    }
}