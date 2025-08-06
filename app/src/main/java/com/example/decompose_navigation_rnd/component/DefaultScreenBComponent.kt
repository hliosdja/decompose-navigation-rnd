package com.example.decompose_navigation_rnd.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.decompose_navigation_rnd.model.UserData

class DefaultScreenBComponent(
    componentContext: ComponentContext,
    private val data: UserData,
    private val onNavigateBack: () -> Unit
): ScreenBComponent, ComponentContext by componentContext {

    private val _userData = MutableValue(UserData())
    override val userData: Value<UserData> = _userData

    init {
        _userData.value = _userData.value.copy(
            name = data.name,
            email = data.email,
            job = data.job
        )
    }

    override fun navigateBack() {
        onNavigateBack()
    }
}