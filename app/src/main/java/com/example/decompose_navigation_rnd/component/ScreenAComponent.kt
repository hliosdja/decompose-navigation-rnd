package com.example.decompose_navigation_rnd.component

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import com.example.decompose_navigation_rnd.component.page_component.EmailInputComponent
import com.example.decompose_navigation_rnd.component.page_component.JobInputComponent
import com.example.decompose_navigation_rnd.component.page_component.NameInputComponent

@OptIn(ExperimentalDecomposeApi::class)
interface ScreenAComponent {
    val pages: Value<ChildPages<*, PageChild>>

    sealed class PageChild {
        data class NameInput(val component: NameInputComponent) : PageChild()
        data class EmailInput(val component: EmailInputComponent) : PageChild()
        data class JobInput(val component: JobInputComponent) : PageChild()
    }
}