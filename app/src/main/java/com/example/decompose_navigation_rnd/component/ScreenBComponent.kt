package com.example.decompose_navigation_rnd.component

import com.arkivanov.decompose.value.Value
import com.example.decompose_navigation_rnd.model.UserData

interface ScreenBComponent {
    val userData: Value<UserData>

    fun navigateBack()
}