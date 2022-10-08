package uz.qmgroup.budget_accounting.datasource.dao

import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import uz.qmgroup.budget_accounting.datasource.entities.PersonEntity
import uz.qmgroup.budget_accounting.datasource.models.Person

class PersonsDao: Dao<Person>() {
    override suspend fun internalInsert(obj: Person): Person = transaction {
        PersonEntity.new {
            this.name = obj.name
            this.balance = obj.balance
        }.toDomainModel()
    }

    override suspend fun internalUpdate(newObj: Person): Person = transaction {
        if (newObj.id == null) throw IllegalArgumentException("Id must not be null for update")
        val entity = PersonEntity[newObj.id]
        entity.name = newObj.name
        entity.balance = newObj.balance
        entity.toDomainModel()
    }

    override suspend fun get(id: Int): Person? = transaction {
        PersonEntity.findById(id)?.toDomainModel()
    }

    override suspend fun find(where: Op<Boolean>): List<Person> = transaction {
        PersonEntity.find(where).map { it.toDomainModel() }
    }
}