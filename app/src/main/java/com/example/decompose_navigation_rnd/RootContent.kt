package com.example.decompose_navigation_rnd

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.decompose_navigation_rnd.component.ScreenAComponent
import com.example.decompose_navigation_rnd.component.ScreenBComponent
import com.example.decompose_navigation_rnd.component.page_component.EmailInputComponent
import com.example.decompose_navigation_rnd.component.page_component.JobInputComponent
import com.example.decompose_navigation_rnd.component.page_component.NameInputComponent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children( // 1
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(slide()), // 2
    ) {
        when (val instance = it.instance) { // 3
            is RootComponent.Child.ScreenA -> {
                ScreenA(instance.component)
            }
            is RootComponent.Child.ScreenB -> {
                ScreenB(instance.component)
            }
        }
    }
}

@OptIn(ExperimentalDecomposeApi::class, ExperimentalFoundationApi::class)
@Composable
private fun ScreenA(component: ScreenAComponent) {
    val pages by component.pages.subscribeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text("Input User Data")
        }
        ChildPages(
            pages = pages,
            onPageSelected = {},
            modifier = Modifier.weight(1f),
            scrollAnimation = PagesScrollAnimation.Default,
        ) { _, page ->
            when (page) {
                is ScreenAComponent.PageChild.NameInput -> {
                    NameInput(page.component)
                }
                is ScreenAComponent.PageChild.EmailInput -> {
                    EmailInput(page.component)
                }
                is ScreenAComponent.PageChild.JobInput -> {
                    JobInput(page.component)
                }
            }
        }
    }
}

@Composable
fun ScreenB(component: ScreenBComponent) {
    val state by component.userData.subscribeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Name: ${state.name}")
        Text("Email: ${state.email}")
        Text("Job: ${state.job}")
        Button(onClick = { component.navigateBack() }) {
            Text("Go Back")
        }
    }
}

@Composable
private fun NameInput(component: NameInputComponent) {
    val (name, setName) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Name Input")
        TextField(
            value = name,
            onValueChange = {
                setName(it)
                component.updateName(it)
            }
        )
        Button(
            onClick = { component.nextPage() }
        ) {
            Text("Next")
        }
    }
}

@Composable
private fun EmailInput(component: EmailInputComponent) {
    val (email, setEmail) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Email Input")
        TextField(
            value = email,
            onValueChange = {
                setEmail(it)
                component.updateEmail(it)
            }
        )
        Button(
            onClick = { component.nextPage() }
        ) {
            Text("Next")
        }
    }
}

@Composable
private fun JobInput(component: JobInputComponent) {
    val (job, setJob) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Job Input")
        TextField(
            value = job,
            onValueChange = {
                setJob(it)
                component.updateJob(it)
            }
        )
        Button(
            onClick = {
                component.submitData()
            }
        ) {
            Text("Submit")
        }
    }
}