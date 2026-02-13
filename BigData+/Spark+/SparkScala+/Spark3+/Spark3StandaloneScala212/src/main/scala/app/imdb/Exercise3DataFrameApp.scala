
package app.imdb

/**
 * Print the list of genres in the alphabetical order.
 */
object Exercise3DataFrameApp {
  def main(args: Array[String]): Unit = {
    import DataFrameFactory.ss.sqlContext.implicits._
    val genres = DataFrameFactory.titleBasicsDf.select("genres").flatMap(row => {
      val genreArray = row.getString(0)
      if (genreArray != null) genreArray.split(",") else Array[String]()
    }).distinct().sort("value").collect()

    val genresStr = genres.mkString(",")
    println(s"Number: ${genres.length}")
    println(s"Genres: $genresStr")
    assert(genres.length == 28)
    assert(genresStr.equals("Action,Adult,Adventure,Animation,Biography,Comedy,Crime,Documentary,Drama,Family,Fantasy," +
      "Film-Noir,Game-Show,History,Horror,Music,Musical,Mystery,News,Reality-TV,Romance,Sci-Fi,Short,Sport,Talk-Show," +
      "Thriller,War,Western"))
  }
}
