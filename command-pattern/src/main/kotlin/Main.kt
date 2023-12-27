fun main(args: Array<String>) {
    val employee = HourlyEmployee(
        name = "kim",
        address = "mind",
        hourlyRate = 100,
        timeCards = listOf(1L, 2L, 3L)
    )

    println(employee.calculatePay())
}
