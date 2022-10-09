package uz.qmgroup.budget_accounting.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import uz.qmgroup.budget_accounting.screens.person.PersonDialog
import uz.qmgroup.budget_accounting.screens.transactions.TransactionDialog

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var newPersonDialogShown by remember { mutableStateOf(false) }
    var newTransactionDialogShown by remember { mutableStateOf(false) }

    val viewModel = remember { HomeScreenViewModel() }

    DisposableEffect(Unit) {
        viewModel.initialize()

        onDispose { viewModel.clear() }
    }

    val state by viewModel.state.collectAsState()

    if (newPersonDialogShown) {
        Dialog(
            onCloseRequest = { newPersonDialogShown = false },
            resizable = false,
            transparent = true,
            undecorated = true
        ) {
            PersonDialog(modifier = Modifier.wrapContentSize(), dismiss = { newPersonDialogShown = false })
        }
    }

    if (newTransactionDialogShown) {
        Dialog(
            onCloseRequest = { newTransactionDialogShown = false },
            resizable = false,
            transparent = true,
            undecorated = true,
        ) {
            TransactionDialog(modifier = Modifier.wrapContentSize(), dismiss = { newTransactionDialogShown = false })
        }
    }

    AnimatedContent(state.javaClass) {
        when (val current = state) {
            HomeScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        CircularProgressIndicator()

                        Text("Loading", style = MaterialTheme.typography.h5)
                    }
                }
            }

            is HomeScreenState.PersonsFetched -> {
                HomeScreenContent(
                    modifier = modifier.fillMaxSize(),
                    onNewPersonClick = { newPersonDialogShown = true },
                    onNew50InvoiceClick = { newTransactionDialogShown = true },
                    persons = current.list
                )
            }
        }
    }
}