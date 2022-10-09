package uz.qmgroup.budget_accounting.screens.home

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.qmgroup.budget_accounting.base.ViewModel
import uz.qmgroup.budget_accounting.datasource.AppDataSource
import uz.qmgroup.budget_accounting.datasource.models.toDomainModel

class HomeScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    fun initialize() {
        viewModelScope.launch {
            launch {
                AppDataSource.getInstance().personTableQueries.getAllPersons().asFlow().mapToList().collect {
                    _state.tryEmit(HomeScreenState.PersonsFetched(it.map { item -> item.toDomainModel() }))
                }
            }
        }
    }
}