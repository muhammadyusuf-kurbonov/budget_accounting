package uz.qmgroup.budget_accounting.datasource

import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import uz.qmgroup.TransactionEntity
import uz.qmgroup.budget_accounting.database.AppDatabase
import uz.qmgroup.budget_accounting.database.TransactionTypes

class AppDataSource private constructor(
    private val database: AppDatabase,
) : AppDatabase by database {
    companion object {
        private var instance: AppDataSource? = null
        private val lock: Any = Any()
        fun getInstance(): AppDataSource = synchronized(lock) {
            if (instance == null) {
                val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:sample.db")
                AppDatabase.Schema.create(driver)
                instance = AppDataSource(
                    AppDatabase(
                        driver, transactionEntityAdapter = TransactionEntity.Adapter(
                            typeAdapter = EnumColumnAdapter()
                        )
                    )
                )
            }

            return instance!!
        }
    }

    fun createInvoice(amount: Double, note: String? = null) {
        transactionQueries.transaction {
            personTableQueries.getAllPersons().executeAsList().forEach {
                transactionQueries.insertTransaction(
                    amount = amount,
                    note = note ?: "",
                    type = TransactionTypes.INVOICE,
                    personId = it.id,
                    id = null
                )

                personTableQueries.updateBalance(
                    it.balance - amount,
                    it.id
                )
            }
        }
    }
}