package uz.qmgroup.budget_accounting.datasource.models

import uz.qmgroup.budget_accounting.database.TransactionTypes

data class Transaction (
    val id: Int? = null,
    val note: String,
    val type: TransactionTypes,
    val amount: Float,
    val person: Person,
)

