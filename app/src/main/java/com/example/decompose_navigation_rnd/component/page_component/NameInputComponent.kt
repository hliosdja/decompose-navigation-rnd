package com.example.decompose_navigation_rnd.component.page_component

import com.arkivanov.decompose.ComponentContext

class NameInputComponent(
    componentContext: ComponentContext,
    private val onNameChanged: (String) -> Unit,
    private val onNextClick: () -> Unit
): ComponentContext by componentContext {
    fun updateName(name: String) = onNameChanged(name)
    fun nextPage() = onNextClick()
}