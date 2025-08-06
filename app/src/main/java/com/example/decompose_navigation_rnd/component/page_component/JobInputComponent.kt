package com.example.decompose_navigation_rnd.component.page_component

import com.arkivanov.decompose.ComponentContext

class JobInputComponent(
    componentContext: ComponentContext,
    private val onJobChanged: (String) -> Unit,
    private val onComplete: () -> Unit
): ComponentContext by componentContext {
    fun updateJob(job: String) = onJobChanged(job)
    fun submitData() = onComplete()
}