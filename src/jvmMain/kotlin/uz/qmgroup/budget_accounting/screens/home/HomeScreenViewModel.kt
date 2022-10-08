package uz.qmgroup.budget_accounting.screens.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.qmgroup.budget_accounting.base.ViewModel
import uz.qmgroup.budget_accounting.datasource.AppDataSource

class HomeScreenViewModel: ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    fun initialize() {
        viewModelScope.launch {
            launch {
                AppDataSource.getInstance().personDao.watchAll().collect {
                    _state.tryEmit(HomeScreenState.PersonsFetched(it))
                }
            }
        }
    }
}