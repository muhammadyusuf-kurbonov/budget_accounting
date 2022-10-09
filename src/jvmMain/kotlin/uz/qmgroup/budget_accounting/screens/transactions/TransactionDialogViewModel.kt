package uz.qmgroup.budget_accounting.screens.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import uz.qmgroup.budget_accounting.base.ViewModel
import uz.qmgroup.budget_accounting.database.TransactionTypes
import uz.qmgroup.budget_accounting.datasource.AppDataSource
import uz.qmgroup.budget_accounting.datasource.models.Person
import uz.qmgroup.budget_accounting.datasource.models.toDomainModel

class TransactionDialogViewModel : ViewModel() {
    var isSaveInProgress by mutableStateOf(false)
    private set

    var isSaveCompleted by mutableStateOf(false)
    private set

    var currentMode by mutableStateOf(TransactionTypes.INVOICE)
    private set

    var selectedPerson by mutableStateOf<Person?>(null)
    private set

    val persons by lazy {
        AppDataSource.getInstance().personTableQueries.getAllPersons().executeAsList().map { it.toDomainModel() }
    }

    fun changeMode(newType: TransactionTypes) {
        if (newType == TransactionTypes.PAYMENT && selectedPerson == null)
            selectedPerson = persons.first()

        currentMode = newType
    }

    fun selectPerson(person: Person) {
        this.selectedPerson = person
    }

    fun save(note: String, amount: Float) {
        isSaveInProgress = true
        viewModelScope.launch {
            val dataSource = AppDataSource.getInstance()
            if (currentMode == TransactionTypes.PAYMENT)
                dataSource
                    .createPayment(
                        note = note,
                        amount = amount.toDouble(),
                        person = selectedPerson ?: throw IllegalStateException(),
                    )
            else
                dataSource.createInvoice(
                        note = note,
                        amount = amount.toDouble(),
                    )
            isSaveCompleted = true
        }
    }

    fun initialize() {
        isSaveCompleted = false
        isSaveInProgress = false
    }
}