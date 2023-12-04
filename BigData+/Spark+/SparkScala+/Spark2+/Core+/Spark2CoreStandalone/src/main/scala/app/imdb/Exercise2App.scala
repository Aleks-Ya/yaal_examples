
package app.imdb

/**
 * Calculate row count in all datasets.
 */
object Exercise2App {
  def main(args: Array[String]): Unit = {
    printRowCount(ImdbTable.titleAkas, 31837339)
    printRowCount(ImdbTable.titleBasics, 8877960)
    printRowCount(ImdbTable.titleCrew, 8877960)
    printRowCount(ImdbTable.titleEpisode, 6661531)
    printRowCount(ImdbTable.titlePrincipals, 50034810)
    printRowCount(ImdbTable.titleRatings, 1238518)
    printRowCount(ImdbTable.nameBasics, 11582534)
  }

  private def printRowCount(imdbDataFrame: ImdbTable.Category, expCount: Long): Unit = {
    val count = DataFrameFactory.datasetByName(imdbDataFrame).count()
    println(s"Table: ${imdbDataFrame.toString}, Row count: $count")
    assert(count == expCount)
  }
}
