package uz.qmgroup.budget_accounting.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uz.qmgroup.budget_accounting.datasource.models.Person
import java.text.NumberFormat

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    persons: List<Person>,
    onNewPersonClick: () -> Unit,
    onNew50InvoiceClick: () -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.align(Alignment.End), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onNew50InvoiceClick) {
                Text("New invoice 50$")
            }

            Button(onClick = onNewPersonClick) {
                Text("New person")
            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            if (persons.isEmpty()) {
                item {
                    Text(
                        "No persons yet",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6
                    )
                }
            } else {
                items(persons) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(it.name, style = MaterialTheme.typography.body1)
                        Text(
                            NumberFormat.getCurrencyInstance().format(it.balance),
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                }
            }
        }
    }
}