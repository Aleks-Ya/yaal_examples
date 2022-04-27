
package app.sixexercises

/**
 * Find out how many sellers are in the data.
 */
object WarmUp1App {
  def main(args: Array[String]): Unit = {
    val sellerCount = DataFrameFactory.sellersDf.count()
    assert(sellerCount == 10)
  }
}
