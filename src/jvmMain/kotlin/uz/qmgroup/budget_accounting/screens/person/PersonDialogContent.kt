package uz.qmgroup.budget_accounting.screens.person

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PersonDialogContent(
    modifier: Modifier,
    name: String,
    onNameUpdate: (String) -> Unit,
    balanceAsString: String,
    onBalanceUpdate: (String) -> Unit,
    save: () -> Unit,
    isSaving: Boolean,
    dismiss: () -> Unit,
) {
    Card(modifier = modifier.padding(16.dp), shape = RoundedCornerShape(6.dp), elevation = 16.dp) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Create new person", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(name, onNameUpdate, modifier = Modifier.fillMaxWidth(), label = {
                Text("Name")
            })

            OutlinedTextField(balanceAsString, onBalanceUpdate, modifier = Modifier.fillMaxWidth(), label = {
                Text("Initial balance")
            })

            Row(modifier = Modifier.align(Alignment.End), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Button(onClick = save, enabled = !isSaving) {
                    Text("Save")
                }

                TextButton(onClick = dismiss) {
                    Text("Cancel")
                }
            }
        }
    }
}
