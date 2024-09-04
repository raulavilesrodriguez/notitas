package com.example.empresa1.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.empresa1.R

/** Navigation destinations */
enum class NoteDestination(
    @StringRes val labelRes: Int,
    val icon: ImageVector
) {
    Notes(R.string.tab_notes, Icons.Default.Home),
    Add(R.string.tab_add, Icons.Default.Add),
    Favorites(R.string.tab_favorites, Icons.Default.Favorite)
}