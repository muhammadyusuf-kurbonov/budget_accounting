package uz.qmgroup.budget_accounting.screens.app

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uz.qmgroup.budget_accounting.datasource.AppScreenState

class AppViewModel {
    private val _currentState = MutableStateFlow<AppScreenState>(AppScreenState.Home)
    val currentState = _currentState.asStateFlow()
}