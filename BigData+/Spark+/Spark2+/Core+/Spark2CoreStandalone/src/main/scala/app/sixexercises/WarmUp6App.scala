
package app.sixexercises

import java.time.LocalDate

/**
 * How many distinct products have been sold in each day.
 */
object WarmUp6App {
  def main(args: Array[String]): Unit = {
    import DataFrameFactory.ss.sqlContext.implicits._
    val dayToDistinctProductNumber = DatasetFactory.salesDs
      .groupByKey(_.date)
      .mapGroups((date, iter) => (date, iter.toList.map(_.product_id).distinct.length))
      .sort($"_2".desc)
      .collect()
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-06"), 100765)))
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-09"), 100501)))
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-01"), 100337)))
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-03"), 100017)))
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-02"), 99807)))
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-05"), 99796)))
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-04"), 99791)))
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-07"), 99756)))
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-08"), 99662)))
    assert(dayToDistinctProductNumber.contains((LocalDate.parse("2020-07-10"), 98973)))
  }
}
