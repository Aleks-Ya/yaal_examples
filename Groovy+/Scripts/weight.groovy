import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

currentDate = LocalDate.now().minusDays(1)
startDate = LocalDate.of(2017, 9, 17)
finishDate = LocalDate.of(2017, 12, 31)
daysTotal = ChronoUnit.DAYS.between(startDate, finishDate)
daysPast = ChronoUnit.DAYS.between(startDate, currentDate)
daysPastPersent = daysPast / daysTotal

startWeight = 86.5
finishWeight = 70.0
weightTotal = startWeight - finishWeight
currentWeight = args[1].toDouble()
previousWeight = args[0].toDouble()
weightChange = startWeight - currentWeight
wightPersent = weightChange / weightTotal
weightChangeDaily = currentWeight - previousWeight

ruRu = new Locale("ru", "RU")

formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
currentDateText = currentDate.format(formatter)

percentFormat = NumberFormat.getPercentInstance(ruRu)
percentFormat.setMaximumFractionDigits(1)
percentFormat.setMinimumFractionDigits(1)
daysPastPercetTxt=percentFormat.format(daysPastPersent)
wightPersentTxt = percentFormat.format(wightPersent)

decimalFormat = new DecimalFormat("##0.0", DecimalFormatSymbols.getInstance(ruRu))
weightChangeTxt = decimalFormat.format(weightChange)
weightTotalTxt = decimalFormat.format(weightTotal)
previousWeightTxt = decimalFormat.format(previousWeight)
currentWeightTxt = decimalFormat.format(currentWeight)
weightChangeDailyTxt = (weightChangeDaily > 0 ? "+" : "") + decimalFormat.format(weightChangeDaily)

message = """
${currentDateText} 
прошло ${daysPast} из ${daysTotal} дней (${daysPastPercetTxt}) 
сброшено ${weightChangeTxt} из ${weightTotalTxt} кг (${wightPersentTxt})
Вес: ${previousWeightTxt} -> ${currentWeightTxt} кг / ${weightChangeDailyTxt} кг
Тренировка вчера:
"""
println(message)