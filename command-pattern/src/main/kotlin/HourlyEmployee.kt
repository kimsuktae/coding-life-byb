class HourlyEmployee(
    private val name: String,
    private val address: String,
    override val hourlyRate: Int,
    override val timeCards: List<Long>,
) : HourlyClassification {
    override fun calculatePay(): Long {
        return timeCards.fold(0) { acc: Long, l: Long -> acc + (l * hourlyRate) }
    }
}
