package com.example.decompose_navigation_rnd

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.decompose_navigation_rnd.component.ScreenAComponent
import com.example.decompose_navigation_rnd.component.ScreenBComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>> // 1

    sealed interface Child { // 2
        class ScreenA(val component: ScreenAComponent) : Child
        class ScreenB(val component: ScreenBComponent) : Child
    }
}