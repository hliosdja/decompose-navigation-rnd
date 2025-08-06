package com.example.decompose_navigation_rnd.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.router.pages.selectFirst
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnResume
import com.example.decompose_navigation_rnd.component.page_component.EmailInputComponent
import com.example.decompose_navigation_rnd.component.page_component.JobInputComponent
import com.example.decompose_navigation_rnd.component.page_component.NameInputComponent
import com.example.decompose_navigation_rnd.model.UserData
import kotlinx.serialization.Serializable

@OptIn(ExperimentalDecomposeApi::class)
class DefaultScreenAComponent(
    componentContext: ComponentContext,
    private val onComplete: (UserData) -> Unit,
): ScreenAComponent, ComponentContext by componentContext {

    private val _consolidatedUserData = MutableValue(UserData())

    private val pageNavigation = PagesNavigation<PageConfig>()

    init {
        lifecycle.doOnResume {
            _consolidatedUserData.value = UserData()
        }
    }

    override val pages: Value<ChildPages<*, ScreenAComponent.PageChild>> = childPages(
        source = pageNavigation,
        serializer = PageConfig.serializer(),
        initialPages = {
            Pages(
                items = listOf(
                    PageConfig.NameInput,
                    PageConfig.EmailInput,
                    PageConfig.JobInput
                ),
                selectedIndex = 0
            )
        },
        childFactory = ::createPages,
    )

    private fun createPages(
        config: PageConfig,
        componentContext: ComponentContext
    ): ScreenAComponent.PageChild  {
        return when (config) {
            PageConfig.NameInput -> ScreenAComponent.PageChild.NameInput(
                component = NameInputComponent(
                    componentContext = componentContext,
                    onNameChanged = { name ->
                        updateUserData { it.copy(name = name) }
                    },
                    onNextClick = { nextPage(null) }
                )
            )
            PageConfig.EmailInput -> ScreenAComponent.PageChild.EmailInput(
                component = EmailInputComponent(
                    componentContext = componentContext,
                    onEmailChanged = { email ->
                        updateUserData { it.copy(email = email) }
                    },
                    onNextClick = { nextPage(null) }
                )
            )
            PageConfig.JobInput -> ScreenAComponent.PageChild.JobInput(
                component = JobInputComponent(
                    componentContext = componentContext,
                    onJobChanged = { job ->
                        updateUserData { it.copy(job = job) }
                    },
                    onComplete = {
                        nextPage(null)
                    }
                )
            )
        }
    }

    private fun updateUserData(update: (UserData) -> UserData) {
        _consolidatedUserData.value = update(_consolidatedUserData.value)
    }

    fun nextPage(page: Int?) {
        val currentPage = pages.value
        val currentIndex = pages.value.selectedIndex

        if (!isCurrentPageValid()) return
        if (page != null) {
            pageNavigation.select(page)
        }
        else if (currentIndex < currentPage.items.size - 1) {
            pageNavigation.select(currentIndex + 1)
        } else {
            onComplete(_consolidatedUserData.value)
            pageNavigation.selectFirst()
        }
    }

    fun isCurrentPageValid(): Boolean {
        val currentIndex = pages.value.selectedIndex

        return when (currentIndex) {
            0 -> _consolidatedUserData.value.name.isNotBlank()
            1 -> _consolidatedUserData.value.email.isNotBlank()
            2 -> _consolidatedUserData.value.job.isNotBlank()
            else -> true
        }
    }

    @Serializable
    sealed class PageConfig {
        @Serializable
        data object NameInput : PageConfig()
        @Serializable
        data object EmailInput : PageConfig()
        @Serializable
        data object JobInput : PageConfig()
    }
}