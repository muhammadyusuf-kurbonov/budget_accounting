package uz.qmgroup.budget_accounting.datasource.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class Person(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<Person>(PersonsTable)

    private object PersonsTable: IntIdTable() {
        val name = varchar("name", 50)
        val balance = float("balance").default(0f)
    }

    var name by PersonsTable.name
    var balance by PersonsTable.balance
}