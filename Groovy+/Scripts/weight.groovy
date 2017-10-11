import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static java.time.temporal.ChronoUnit.DAYS

//Parameters
currentWeight = args[1].toDouble()
previousWeight = args[0].toDouble()

//Date
currentDate = LocalDate.now().minusDays(1)
startDate = LocalDate.of(2017, 9, 17)
finishDate = LocalDate.of(2017, 12, 31)
daysTotal = DAYS.between(startDate, finishDate)
daysPast = DAYS.between(startDate, currentDate)
daysRemain = DAYS.between(currentDate, finishDate)
daysPastPersent = daysPast / daysTotal

//Weight
startWeight = 86.5
finishWeight = 70.0
weightTotal = startWeight - finishWeight
weightRemain = currentWeight - finishWeight
weightChange = startWeight - currentWeight
wightPersent = weightChange / weightTotal
weightChangeDaily = currentWeight - previousWeight
weightPerDay = weightRemain / daysRemain
weightPerWeek = weightPerDay * 7

//Text
ruRu = new Locale("ru", "RU")

formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
currentDateText = currentDate.format(formatter)

percentFormat = NumberFormat.getPercentInstance(ruRu)
percentFormat.setMaximumFractionDigits(1)
percentFormat.setMinimumFractionDigits(1)
daysPastPercetTxt=percentFormat.format(daysPastPersent)
wightPersentTxt = percentFormat.format(wightPersent)

decimalFormat1 = new DecimalFormat("##0.0", DecimalFormatSymbols.getInstance(ruRu))
decimalFormat2 = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(ruRu))
weightChangeTxt = decimalFormat1.format(weightChange)
weightTotalTxt = decimalFormat1.format(weightTotal)
previousWeightTxt = decimalFormat1.format(previousWeight)
currentWeightTxt = decimalFormat1.format(currentWeight)
weightChangeDailyTxt = (weightChangeDaily > 0 ? "+" : "") + decimalFormat1.format(weightChangeDaily)
weightPerDayTxt = decimalFormat2.format(weightPerDay)
weightPerWeekTxt = decimalFormat2.format(weightPerWeek)

//Output
println """
${currentDateText} 
прошло ${daysPast} из ${daysTotal} дней (${daysPastPercetTxt}) 
сброшено ${weightChangeTxt} из ${weightTotalTxt} кг (${wightPersentTxt})
Вес: ${previousWeightTxt} -> ${currentWeightTxt} кг / ${weightChangeDailyTxt} кг
Чтобы успеть, нужно скидывать по: ${weightPerWeekTxt} кг в неделю, по ${weightPerDayTxt} кг в день
Тренировка вчера:
Еда до тренировки:
Еда после тренировки:
"""