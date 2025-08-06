package com.example.decompose_navigation_rnd.component.page_component

import com.arkivanov.decompose.ComponentContext

class EmailInputComponent(
    componentContext: ComponentContext,
    private val onEmailChanged: (String) -> Unit,
    private val onNextClick: () -> Unit
): ComponentContext by componentContext {
    fun updateEmail(email: String) = onEmailChanged(email)
    fun nextPage() = onNextClick()
}
