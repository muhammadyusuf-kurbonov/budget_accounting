package uz.qmgroup.budget_accounting.datasource.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Op

abstract class Dao<T> {
    private val mutex: Mutex = Mutex()

    suspend fun insert(obj: T): T = withContext(Dispatchers.IO) {
        val result = internalInsert(obj)
        mutex.unlock()
        result
    }
    suspend fun update(obj: T): T = withContext(Dispatchers.IO) {
        val result = internalUpdate(obj)
        mutex.unlock()
        result
    }

    protected abstract suspend fun internalInsert(obj: T): T
    protected abstract suspend fun internalUpdate(newObj: T): T
    protected abstract suspend fun get(id: Int): T?
    protected abstract suspend fun find(where: Op<Boolean>): List<T>

    fun watch(query: Op<Boolean>) = flow {
        emit(find(query))
        while (currentCoroutineContext().isActive) {
            if (!mutex.isLocked) {
                emit(find(query))
                mutex.lock()
            }
        }
    }

    fun watchAll() = flow {
        emit(find(Op.TRUE))
        while (currentCoroutineContext().isActive) {
            emit(find(Op.TRUE))
            mutex.lock()
        }
    }
}