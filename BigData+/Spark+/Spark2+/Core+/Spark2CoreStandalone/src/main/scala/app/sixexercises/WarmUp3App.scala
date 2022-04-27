
package app.sixexercises

/**
 * Find out how many orders are in the data.
 */
object WarmUp3App {
  def main(args: Array[String]): Unit = {
    val saleCount = DataFrameFactory.salesDf.count()
    assert(saleCount == 20000040)
  }
}
