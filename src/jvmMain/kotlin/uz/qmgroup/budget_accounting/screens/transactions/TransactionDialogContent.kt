package uz.qmgroup.budget_accounting.screens.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import uz.qmgroup.budget_accounting.database.TransactionTypes
import uz.qmgroup.budget_accounting.datasource.models.Person

@Composable
fun TransactionDialogContent(
    modifier: Modifier,
    currentMode: TransactionTypes,
    onCurrentModeChanged: (TransactionTypes) -> Unit,
    note: String,
    onNoteUpdate: (String) -> Unit,
    amountAsString: String,
    onAmountUpdate: (String) -> Unit,
    save: () -> Unit,
    isSaving: Boolean,
    dismiss: () -> Unit,
    getPersons: () -> List<Person>,
    selectedPerson: Person?,
    selectPerson: (Person) -> Unit
) {
    Card(modifier = modifier.padding(16.dp), shape = RoundedCornerShape(6.dp), elevation = 16.dp) {
        Column(
            modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "Create new " + if (currentMode == TransactionTypes.INVOICE) "invoice" else "payment",
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(4.dp))

            Box {
                var expanded by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = if (currentMode == TransactionTypes.INVOICE) "Invoice" else "Payment",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth().onFocusChanged { expanded = it.hasFocus },
                    readOnly = true,
                )

                DropdownMenu(expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(onClick = {
                        onCurrentModeChanged(TransactionTypes.INVOICE)
                        expanded = false
                    }) {
                        Text("Invoice")
                    }

                    DropdownMenuItem(onClick = {
                        onCurrentModeChanged(TransactionTypes.PAYMENT)
                        expanded = false
                    }) {
                        Text("Payment")
                    }
                }
            }

            if (currentMode == TransactionTypes.PAYMENT) {
                Box {
                    var expanded by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = selectedPerson?.name ?: "Select person",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth().onFocusChanged { expanded = it.hasFocus },
                        readOnly = true,
                    )

                    DropdownMenu(expanded, onDismissRequest = { expanded = false }) {
                        getPersons().forEach {
                            DropdownMenuItem(onClick = {
                                selectPerson(it)
                                expanded = false
                            }) {
                                Text(it.name)
                            }
                        }
                    }
                }
            }

            OutlinedTextField(amountAsString, onAmountUpdate, modifier = Modifier.fillMaxWidth(), label = {
                Text("amount")
            })

            OutlinedTextField(note, onNoteUpdate, modifier = Modifier.fillMaxWidth(), label = {
                Text("Note")
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
