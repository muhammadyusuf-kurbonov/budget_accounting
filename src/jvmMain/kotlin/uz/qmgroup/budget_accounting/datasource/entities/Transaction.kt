package uz.qmgroup.budget_accounting.datasource.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class Transaction(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<PersonEntity>(TransactionsTable)

    enum class TransactionTypes {
        PAYMENT,
        INVOICE
    }

    private object TransactionsTable: IntIdTable() {
        val note = varchar("note", 50)
        val amount = long("amount")
        val type = enumeration<TransactionTypes>("type")
    }
}