package uz.qmgroup.budget_accounting.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import uz.qmgroup.budget_accounting.datasource.models.Person
import uz.qmgroup.budget_accounting.datasource.models.Transaction

class AppDataSource private constructor() {
    companion object {
        private var instance: AppDataSource? = null
        private val lock: Any = Any()
        fun getInstance(): AppDataSource = synchronized(lock) {
            if (instance == null) {
                Database.connect("jdbc:sqlite:sample.db")

                transaction {
                    addLogger(StdOutSqlLogger)

                    SchemaUtils.createMissingTablesAndColumns(
                        Person.table,
                        Transaction.table
                    )
                }

                instance = AppDataSource()
            }
            return instance!!
        }
    }

    suspend fun createPerson(person: Person.() -> Unit) = withContext(Dispatchers.IO) {
        transaction {
            Person.new(person)

            this.commit()
        }
    }

    suspend fun getAllPersons(): List<Person> = withContext(Dispatchers.IO) {
        transaction {
            Person.all().toList()
        }
    }
}