package uz.qmgroup.budget_accounting.datasource

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import uz.qmgroup.budget_accounting.datasource.dao.PersonsDao
import uz.qmgroup.budget_accounting.datasource.entities.PersonEntity
import uz.qmgroup.budget_accounting.datasource.entities.Transaction

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
                        PersonEntity.table,
                        Transaction.table
                    )
                }

                instance = AppDataSource()
            }
            return instance!!
        }
    }
    val personDao by lazy { PersonsDao() }
}