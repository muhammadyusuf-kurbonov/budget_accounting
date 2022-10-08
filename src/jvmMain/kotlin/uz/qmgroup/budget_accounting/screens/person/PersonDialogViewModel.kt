package uz.qmgroup.budget_accounting.screens.person

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.qmgroup.budget_accounting.base.ViewModel
import uz.qmgroup.budget_accounting.datasource.AppDataSource
import uz.qmgroup.budget_accounting.datasource.models.Person
import java.text.NumberFormat

class PersonDialogViewModel: ViewModel() {
    private val _state = MutableStateFlow(
        PersonDialogState(
            name = "",
            initialBalance = 0f
        )
    )
    val state = _state.asStateFlow()

    fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun updateBalance(balanceAsString: String) {
        val balance = try {
            NumberFormat.getCurrencyInstance().parse(balanceAsString)
        } catch (e: NumberFormatException) {
            0L
        }
        _state.update { it.copy(initialBalance = balance.toFloat())}
    }

    fun save() {
        _state.update { it.copy(isSaving = true)}
        viewModelScope.launch {
            AppDataSource.getInstance()
                .personDao
                .insert(Person(name = _state.value.name, balance =  _state.value.initialBalance))
            _state.update { it.copy(isSaving = false, isSaveCompleted = true)}
        }
    }
}