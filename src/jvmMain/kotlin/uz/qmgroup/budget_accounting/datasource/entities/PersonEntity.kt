package uz.qmgroup.budget_accounting.datasource.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import uz.qmgroup.budget_accounting.datasource.models.Person

class PersonEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PersonEntity>(PersonsTable)

    private object PersonsTable : IntIdTable() {
        val name = varchar("name", 50)
        val balance = float("balance").default(0f)
    }

    var name by PersonsTable.name
    var balance by PersonsTable.balance

    fun toDomainModel() = Person(this.id.value, name, balance)
}