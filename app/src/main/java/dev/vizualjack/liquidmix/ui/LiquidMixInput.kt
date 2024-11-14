package dev.vizualjack.liquidmix.ui

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vizualjack.liquidmix.LiquidMixApp
import dev.vizualjack.liquidmix.data.AmountType
import dev.vizualjack.liquidmix.ui.theme.LiquidMixTheme

@Composable
fun LiquidMixInput(
    desiredAmount: Int,
    onDesiredAmountChanged: (Int) -> Unit,
    aromaAmount: Int,
    onAromaAmountChanged: (Int) -> Unit,
    aromaAmountType: AmountType,
    onAromaAmountTypeChanged: (AmountType) -> Unit,
    baseVgPercent: Int,
    onBaseVgPercentChanged: (Int) -> Unit,
    baseWaterPercent: Int,
    onBaseWaterPercentChanged: (Int) -> Unit,
    onCalcClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "desired ml", //stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.bodyLarge
        )
        EditNumberField(
            text = "",
            value = desiredAmount,
            onValueChanged = {
                onDesiredAmountChanged(it)
            },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .width(200.dp)
        )
        Text(
            text = "aroma amount", //stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.bodyLarge
        )
        Row {
            EditNumberField(
                text = "",
                value = aromaAmount,
                onValueChanged = {
                    onAromaAmountChanged(it)
                },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .width(100.dp)
            )
            AmountTypeDropdown(
                value = aromaAmountType,
                onValueChanged = {
                     onAromaAmountTypeChanged(it)
                },
                modifier = Modifier
                    .width(90.dp)
            )
        }
        Text(
            text = "base", //stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.bodyLarge
        )
        Row(modifier = Modifier.height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "VG", //stringResource(R.string.tip_amount, tip),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.width(40.dp)
            )
            EditNumberField(
                text = "",
                value = baseVgPercent,
                onValueChanged = {
                    onBaseVgPercentChanged(it)
                },
                modifier = Modifier
                    .width(100.dp)
            )
            Text(
                text = "%", //stringResource(R.string.tip_amount, tip),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.height(50.dp),
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = "H₂O", //stringResource(R.string.tip_amount, tip),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.width(40.dp)
            )
            EditNumberField(
                text = "",
                value = baseWaterPercent,
                onValueChanged = {
                    onBaseWaterPercentChanged(it)
                },
                modifier = Modifier
                    .width(100.dp)
            )
            Text(
                text = "%", //stringResource(R.string.tip_amount, tip),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { onCalcClicked() }) {
            Text(text = "Calc")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalStdlibApi::class)
@Composable
fun AmountTypeDropdown(
    value: AmountType,
    onValueChanged: (AmountType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = value.text,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                AmountType.values().forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.text) },
                        onClick = {
                            onValueChanged(it)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    text: String,
    value: Int,
    onValueChanged: (Int) -> Unit,
    modifier: Modifier
) {
    var label: @Composable (() -> Unit)? = null
    if (text != "") label = @Composable {
        Text(text)
    }
    var valueAsStr = ""
    if (value > 0) valueAsStr = value.toString()
    TextField(
        value = valueAsStr,
        singleLine = true,
        modifier = modifier,
        onValueChange = { onValueChanged(it.toIntOrNull() ?: 0) },
        label = label,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview(showBackground = true)
@Composable
fun LiquidMixInputPreview() {
    LiquidMixTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LiquidMixInput(
                desiredAmount = 0,
                onDesiredAmountChanged = {},
                baseWaterPercent = 0,
                baseVgPercent = 0,
                aromaAmountType = AmountType.ML,
                aromaAmount = 0,
                onCalcClicked = {},
                onAromaAmountChanged = {},
                onAromaAmountTypeChanged = {},
                onBaseVgPercentChanged = {},
                onBaseWaterPercentChanged = {}
            )
        }

    }
}