package uz.qmgroup.budget_accounting.screens.home

import uz.qmgroup.budget_accounting.datasource.models.Person

sealed class HomeScreenState {
    object Loading: HomeScreenState()

    class PersonsFetched(val list: List<Person>): HomeScreenState()
}