package uz.qmgroup.budget_accounting.screens.person

data class PersonDialogState(
    val isSaving: Boolean = false,
    val name: String,
    val initialBalance: Float,
    val isSaveCompleted: Boolean = false,
)
