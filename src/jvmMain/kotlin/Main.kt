// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import uz.qmgroup.budget_accounting.screens.app.AppScreen
import uz.qmgroup.budget_accounting.screens.app.AppViewModel

fun main() = application {
    val viewModel = remember { AppViewModel() }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Budget accounting",
        undecorated = false,
        resizable = true,
    ) {
        AppScreen(viewModel)
    }
}
