package com.example.decompose_navigation_rnd

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.decompose_navigation_rnd.component.DefaultScreenAComponent
import com.example.decompose_navigation_rnd.component.DefaultScreenBComponent
import com.example.decompose_navigation_rnd.model.UserData
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>() // 1

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack( // 2
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.ScreenA,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        Config.ScreenA -> RootComponent.Child.ScreenA(
            DefaultScreenAComponent(
                componentContext = componentContext,
                onComplete = { navigation.pushNew(Config.ScreenB(it)) }
            )
        )

        is Config.ScreenB -> RootComponent.Child.ScreenB(
            DefaultScreenBComponent(
                componentContext = componentContext,
                data = config.data,
                onNavigateBack = { navigation.pop() }
            )
        )
    }

    @Serializable
    private sealed interface Config { // 6
        @Serializable
        data object ScreenA : Config
        @Serializable
        data class ScreenB(val data: UserData) : Config
    }
}