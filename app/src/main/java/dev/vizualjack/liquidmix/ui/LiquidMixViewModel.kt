package dev.vizualjack.liquidmix.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import dev.vizualjack.liquidmix.data.AmountType
import dev.vizualjack.liquidmix.data.LiquidMixResultState
import dev.vizualjack.liquidmix.data.LiquidMixState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

class LiquidMixViewModel : ViewModel() {
    private val _uiInputState = MutableStateFlow(LiquidMixState())
    private val _uiResultState = MutableStateFlow(LiquidMixResultState())
    val uiInputState: StateFlow<LiquidMixState> = _uiInputState.asStateFlow()
    val uiResultState: StateFlow<LiquidMixResultState> = _uiResultState.asStateFlow()

    fun setDesiredAmount(newDesiredAmount:Int) {
        _uiInputState.update { currentState ->
            currentState.copy(
                desiredAmount = newDesiredAmount
            )
        }
    }

    fun setAromaAmount(newAromaAmount:Int) {
        _uiInputState.update { currentState ->
            currentState.copy(
                aromaAmount = newAromaAmount
            )
        }
    }

    fun setAromaAmountType(newAromaAmountType:AmountType) {
        _uiInputState.update { currentState ->
            currentState.copy(
                aromaAmountType = newAromaAmountType
            )
        }
    }

    fun setBaseVgPercent(newBaseVgPercent:Int) {
        _uiInputState.update { currentState ->
            currentState.copy(
                baseVgPercent = newBaseVgPercent,
                baseWaterPercent = 100 - newBaseVgPercent
            )
        }
    }

    fun setBaseWaterPercent(newBaseWaterPercent:Int) {
        _uiInputState.update { currentState ->
            currentState.copy(
                baseVgPercent = 100 - newBaseWaterPercent,
                baseWaterPercent = newBaseWaterPercent
            )
        }
    }

    fun canCalculate(): Boolean {
        return  isFilled(uiInputState.value.desiredAmount) &&
                isFilled(uiInputState.value.aromaAmount) &&
                isFilled(uiInputState.value.baseVgPercent) &&
                isFilled(uiInputState.value.baseWaterPercent)
    }

    fun isFilled(value:Int): Boolean {return value > 0}

    val LOG_TAG = "LiquidMixViewModel"

    fun getHighestFloatIndex(vararg vals:Float):Int {
        var highestFloat = 0f
        val valAsList = ArrayList<Float>()
        vals.forEach {
            if(it > highestFloat) highestFloat = it
            valAsList.add(it)
        }
        if (highestFloat == 0f) return -1
        return valAsList.indexOf(highestFloat)
    }

    fun calculate():Boolean {
        val liquidMixData = uiInputState.value
        var desiredAmount = liquidMixData.desiredAmount
        var aromaAmount = liquidMixData.aromaAmount.toFloat()
        if (liquidMixData.aromaAmountType == AmountType.Percent)
            aromaAmount = desiredAmount * aromaAmount / 100
        var baseAmount = desiredAmount - aromaAmount
        var vgAmount = baseAmount * liquidMixData.baseVgPercent / 100
        var waterAmount = baseAmount * liquidMixData.baseWaterPercent / 100
//        Log.d(LOG_TAG,"aromaAmount: " + aromaAmount.roundToInt())
//        Log.d(LOG_TAG,"vgAmount: " + vgAmount.roundToInt())
//        Log.d(LOG_TAG,"waterAmount: " + waterAmount.roundToInt())
        var totalAmount = aromaAmount.roundToInt() + vgAmount.roundToInt() + waterAmount.roundToInt()
        if (totalAmount > desiredAmount) {
            val diff = totalAmount - desiredAmount
            val highestValIndex = getHighestFloatIndex(aromaAmount,vgAmount,waterAmount)
            when(highestValIndex) {
                0 -> aromaAmount -= diff
                1 -> vgAmount -= diff
                2 -> waterAmount -= diff
            }
        }
//        Log.d(LOG_TAG,"aromaAmount: " + aromaAmount.roundToInt())
//        Log.d(LOG_TAG,"vgAmount: " + vgAmount.roundToInt())
//        Log.d(LOG_TAG,"waterAmount: " + waterAmount.roundToInt())
//        Log.d(LOG_TAG,"totalAmount: " + desiredAmount)
//        Log.d(LOG_TAG,"totalAmountCheckSum: " + totalAmountCheckSum)
        var totalAmountCheckSum = aromaAmount.roundToInt() + vgAmount.roundToInt() + waterAmount.roundToInt()
        if (desiredAmount != totalAmountCheckSum) {
            Log.d(LOG_TAG,"calculate(): checksum NOT MATCH")
            return false
        }
        _uiResultState.update {
            LiquidMixResultState(desiredAmount, aromaAmount.roundToInt(),waterAmount.roundToInt(), vgAmount.roundToInt())
        }
        return true
    }
}