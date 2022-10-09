package uz.qmgroup.budget_accounting.screens.transactions

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import java.text.NumberFormat
import java.text.ParseException

@Composable
fun TransactionDialog(
    modifier: Modifier = Modifier,
    dismiss: () -> Unit,
) {
    val viewModel = remember { TransactionDialogViewModel() }

    var note by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf(0f) }

    DisposableEffect(Unit) {
        viewModel.initialize()
        onDispose { viewModel.clear() }
    }

    LaunchedEffect(viewModel.isSaveCompleted) {
        if (viewModel.isSaveCompleted)
            dismiss()
    }

    TransactionDialogContent(
        modifier = modifier,
        note = note,
        onNoteUpdate = { note = it },
        amountAsString = NumberFormat.getCurrencyInstance().format(amount),
        onAmountUpdate = {
            amount = try {
                NumberFormat.getCurrencyInstance().parse(it).toFloat()
            } catch (e: ParseException) {
                0f
            }
        },
        save = { viewModel.save(note, amount) },
        isSaving = viewModel.isSaveInProgress,
        dismiss = dismiss,
        currentMode = viewModel.currentMode,
        onCurrentModeChanged = viewModel::changeMode,
        getPersons = { viewModel.persons },
        selectedPerson = viewModel.selectedPerson,
        selectPerson = viewModel::selectPerson
    )
}
