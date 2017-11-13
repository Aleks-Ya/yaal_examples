import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static java.time.temporal.ChronoUnit.DAYS

//Date
startDate = LocalDate.of(2017, 9, 17)
finishDate = LocalDate.of(2017, 12, 31)
currentDate = LocalDate.now().minusDays(1)
dayBeforeDate = currentDate.minusDays(1)
daysTotal = DAYS.between(startDate, finishDate)
daysPast = DAYS.between(startDate, currentDate)
daysRemain = DAYS.between(currentDate, finishDate)
daysPastPersent = daysPast / daysTotal

//File
homeDir = System.getProperty('user.home')
File file = new File(homeDir, "weight.txt")

//Parameters
if (args.length == 2) {
    currentWeight = args[1].toDouble()
    previousWeight = args[0].toDouble()
} else if (args.length == 1) {
    currentWeight = args[0].toDouble()

    lines = Files.readAllLines(file.toPath()).reverse()
    previousWeight = lines.stream()
            .map {line -> line.split(" ")}
            .map {array -> new Tuple2<LocalDate, Double>(LocalDate.parse(array[0]), Double.parseDouble(array[1]))}
            .filter {tuple -> (tuple.first() == dayBeforeDate) }
            .map {tuple -> tuple.second}
            .findAny().orElseThrow{new IllegalStateException("Can't find weight for date: $dayBeforeDate in file ${file.absolutePath}.")}
} else {
    throw new IllegalArgumentException("Expected one or two arguments. But got: ${args}")
}

Files.write(file.toPath(), ["$currentDate $currentWeight"], StandardOpenOption.APPEND, StandardOpenOption.CREATE)

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

formatter = DateTimeFormatter.ofPattern("dd.MM.yy E").withLocale(new Locale("ru"))
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
Отчет за ${currentDateText} 
прошло ${daysPast} из ${daysTotal} дней (${daysPastPercetTxt}) 
сброшено ${weightChangeTxt} из ${weightTotalTxt} кг (${wightPersentTxt})
Вес: ${previousWeightTxt} -> ${currentWeightTxt} кг / ${weightChangeDailyTxt} кг
Чтобы успеть, нужно скидывать по: ${weightPerWeekTxt} кг в неделю, по ${weightPerDayTxt} кг в день
Тренировка вчера:
Еда до тренировки:
Еда после тренировки:
"""