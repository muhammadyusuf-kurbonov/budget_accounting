package uz.qmgroup.budget_accounting.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

open class ViewModel {
    protected val viewModelScope = CoroutineScope(Job() + Dispatchers.Default)

    open fun onClear() {}

    fun clear() {
        onClear()
        viewModelScope.coroutineContext.cancel()
    }
}