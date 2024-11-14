package dev.vizualjack.liquidmix

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.vizualjack.liquidmix.ui.LiquidMixInput
import dev.vizualjack.liquidmix.ui.LiquidMixResult
import dev.vizualjack.liquidmix.ui.LiquidMixViewModel


/**
 * enum values that represent the screens in the app
 */
enum class LiquidMixScreen() {
    Input,
    Result
}

@Composable
fun LiquidMixApp(
    viewModel: LiquidMixViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = LiquidMixScreen.Input.name,
            modifier = Modifier.padding(10.dp)
        ) {
            composable(route = LiquidMixScreen.Input.name) {
                val uiState by viewModel.uiInputState.collectAsState()
                LiquidMixInput(
                    desiredAmount = uiState.desiredAmount,
                    onDesiredAmountChanged = {
                        viewModel.setDesiredAmount(it)
                    },
                    aromaAmount = uiState.aromaAmount,
                    onAromaAmountChanged = {
                       viewModel.setAromaAmount(it)
                    },
                    aromaAmountType = uiState.aromaAmountType,
                    onAromaAmountTypeChanged = {
                        viewModel.setAromaAmountType(it)
                    },
                    baseVgPercent = uiState.baseVgPercent,
                    onBaseVgPercentChanged = {
                        viewModel.setBaseVgPercent(it)
                    },
                    baseWaterPercent = uiState.baseWaterPercent,
                    onBaseWaterPercentChanged = {
                        viewModel.setBaseWaterPercent(it)
                    },
                    onCalcClicked = {
                        if (viewModel.canCalculate() && viewModel.calculate()) {
                            navController.navigate(LiquidMixScreen.Result.name)
                        }
                    }
                )
            }
            composable(route = LiquidMixScreen.Result.name) {
                val uiState by viewModel.uiResultState.collectAsState()
                LiquidMixResult(
                    result = uiState,
                    onBackClicked = {
                        navController.navigate(LiquidMixScreen.Input.name)
                    }
                )
            }
        }
    }
}