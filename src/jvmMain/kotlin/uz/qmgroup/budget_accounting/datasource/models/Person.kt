package uz.qmgroup.budget_accounting.datasource.models

data class Person(
    val id: Long? = null,
    val name: String,
    val balance: Double,
)