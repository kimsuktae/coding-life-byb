interface HourlyClassification : PayClassification {
    val hourlyRate: Int
    val timeCards: List<Long>
}
