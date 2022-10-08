package uz.qmgroup.budget_accounting.screens.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uz.qmgroup.budget_accounting.datasource.AppScreenState
import uz.qmgroup.budget_accounting.screens.home.HomeScreen
import uz.qmgroup.budget_accounting.theme.AppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun AppScreen(
    viewModel: AppViewModel
) {
    val state by viewModel.currentState.collectAsState()

    AppTheme {
        AnimatedContent(modifier = Modifier.fillMaxSize().padding(16.dp), targetState = state) {
            when (it) {
                is AppScreenState.Home -> {
                    HomeScreen()
                }
            }
        }
    }
}