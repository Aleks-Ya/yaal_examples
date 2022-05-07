
package app.imdb

/**
 * Print dataset schemas: 1) Dataset name, 2) Schema, 3) Show.
 */
object Exercise1App {
  def main(args: Array[String]): Unit = {
    printInfo(ImdbTable.titleAkas)
    printInfo(ImdbTable.titleBasics)
    printInfo(ImdbTable.titleCrew, 8877960)
    printInfo(ImdbTable.titleEpisode, 6661531)
    printInfo(ImdbTable.titlePrincipals, 50034810)
    printInfo(ImdbTable.titleRatings, 1238518)
    printInfo(ImdbTable.nameBasics, 11582534)
  }

  private def printInfo(imdbDataFrame: ImdbTable.Category): Unit = {
    println("--------------------------------------------------------------")
    println(s"Table: ${imdbDataFrame.toString}")
    val df = DataFrameFactory.datasetByName(imdbDataFrame)
    df.cache()
    df.printSchema()
    df.show()
  }
}
