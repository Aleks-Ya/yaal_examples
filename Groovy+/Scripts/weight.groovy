import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

currentDate = LocalDate.now().minusDays(1)
startDate = LocalDate.of(2017, 9, 17)
finishDate = LocalDate.of(2017, 12, 31)
daysTotal = ChronoUnit.DAYS.between(startDate, finishDate)
daysPast = ChronoUnit.DAYS.between(startDate, currentDate)
daysPastPersent = Math.round(daysPast * 100 * 10 / daysTotal) / 10
daysPastPersetTxt = daysPastPersent.toString().replace(".", ",")

persentPast =
startWeight = 86.5
finishWeight = 70.0
weightTotal = startWeight - finishWeight
currentWeight = args[0].toDouble()
weightChange = startWeight - currentWeight
wightPersent = Math.round(weightChange * 10 / weightTotal) / 10

formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
currentDateText = currentDate.format(formatter)

message = """${currentDateText} / прошло ${daysPast} из ${daysTotal} дней (${daysPastPersent}%) / сброшено ${
    weightChange
} из ${weightTotal} кг (${wightPersent}%)
Вес: ${currentWeight} кг, ${weightChange} кг
"""
println(message)