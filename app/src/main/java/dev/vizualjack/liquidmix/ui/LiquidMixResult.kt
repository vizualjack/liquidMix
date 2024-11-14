package dev.vizualjack.liquidmix.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vizualjack.liquidmix.LiquidMixApp
import dev.vizualjack.liquidmix.R
import dev.vizualjack.liquidmix.data.LiquidMixResultState
import dev.vizualjack.liquidmix.ui.theme.AromaColor
import dev.vizualjack.liquidmix.ui.theme.LiquidMixTheme
import dev.vizualjack.liquidmix.ui.theme.VgColor
import dev.vizualjack.liquidmix.ui.theme.WaterColor
import java.text.NumberFormat


@Composable
fun LiquidMixResult(
    result: LiquidMixResultState,
    onBackClicked: () -> Unit) {

    val totalHeight = 500f
    val minHeight = 20f
    var aromaHeight = result.aromaAmount.toFloat() / result.totalAmount.toFloat() * totalHeight
    var waterHeight = result.waterAmount.toFloat() / result.totalAmount.toFloat() * totalHeight
    var vgHeight = result.vgAmount.toFloat() / result.totalAmount.toFloat() * totalHeight
    aromaHeight = Math.max(minHeight, aromaHeight)
    waterHeight = Math.max(minHeight, waterHeight)
    vgHeight = Math.max(minHeight, vgHeight)

    Column(
        modifier = Modifier.fillMaxSize().padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(result.totalAmount.toString() + " ml")
        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier
            .height(totalHeight.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(20.dp)),
            verticalArrangement = Arrangement.Bottom) {
            Column(modifier = Modifier
                .background(AromaColor)
                .height(aromaHeight.dp)
                .width(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = result.aromaAmount.toString() + " ml Aroma")
            }
            Column(modifier = Modifier
                .background(WaterColor)
                .height(waterHeight.dp)
                .width(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = result.waterAmount.toString() + " ml H₂O")
            }
            Column(modifier = Modifier
                .background(VgColor)
                .height(vgHeight.dp)
                .width(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = result.vgAmount.toString() + " ml VG")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { onBackClicked() }) {
            Text(text = "Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LiquidMixResultPreview() {
    LiquidMixTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LiquidMixResult(
                result = LiquidMixResultState(
                    totalAmount = 80,
                    aromaAmount = 23,
                    waterAmount = 7,
                    vgAmount = 60,
                ),
                onBackClicked = {}
            )
        }
    }
}