package uz.qmgroup.budget_accounting.datasource.models

import uz.qmgroup.PersonTable

fun PersonTable.toDomainModel() = Person(
    id = this.id,
    name = this.name,
    balance = this.balance
)