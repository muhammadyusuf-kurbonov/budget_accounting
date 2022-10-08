package uz.qmgroup.budget_accounting.screens.person

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import java.text.NumberFormat

@Composable
fun PersonDialog(
    modifier: Modifier = Modifier,
    dismiss: () -> Unit,
) {
    val viewModel = remember { PersonDialogViewModel() }

    DisposableEffect(Unit) {
        viewModel.initialize()
        onDispose { viewModel.clear() }
    }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        if (state.isSaveCompleted)
            dismiss()
    }

    PersonDialogContent(
        modifier = modifier,
        name = state.name,
        balanceAsString = NumberFormat.getCurrencyInstance().format(state.initialBalance),
        onNameUpdate = viewModel::updateName,
        onBalanceUpdate = viewModel::updateBalance,
        save = viewModel::save,
        isSaving = state.isSaving,
        dismiss = dismiss,
    )
}
