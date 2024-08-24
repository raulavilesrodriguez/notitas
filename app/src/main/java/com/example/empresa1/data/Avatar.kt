package com.example.empresa1.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Avatar(
    @DrawableRes val imageId: Int,
    @StringRes val description: Int,
)
