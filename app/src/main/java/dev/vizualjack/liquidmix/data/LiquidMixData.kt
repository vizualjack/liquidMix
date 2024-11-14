package dev.vizualjack.liquidmix.data

enum class AmountType(val text:String) {
    ML(text = "ml"),
    Percent(text = "%")
}

data class LiquidMixState(
    val desiredAmount: Int = 0,
    val aromaAmount: Int = 0,
    val aromaAmountType: AmountType = AmountType.ML,
    val baseVgPercent: Int = 0,
    val baseWaterPercent: Int = 0,
)

data class LiquidMixResultState(
    val totalAmount: Int = 0,
    val aromaAmount: Int = 0,
    val waterAmount: Int = 0,
    val vgAmount: Int = 0,
)