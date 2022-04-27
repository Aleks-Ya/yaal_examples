
package app.imdb

import org.apache.spark.sql.{Encoder, Encoders}

/**
 * Print the list of genres in the alphabetical order.
 */
object Exercise2DatasetApp {
  def main(args: Array[String]): Unit = {
    implicit val mapEncoder: Encoder[Genre] = Encoders.product[Genre]
    val genres = DataFrameFactory.titleBasicsDf.select("genres").as[Genre]
      .flatMap(genre => if (genre.genres != null) genre.genres.split(",").map(Genre) else Array[Genre]())
      .distinct().sort("genres").collect()

    val genresStr = genres.map(_.genres).mkString(",")
    println(s"Number: ${genres.length}")
    println(s"Genres: $genresStr")
    assert(genres.length == 28)
    assert(genresStr.equals("Action,Adult,Adventure,Animation,Biography,Comedy,Crime,Documentary,Drama,Family,Fantasy," +
      "Film-Noir,Game-Show,History,Horror,Music,Musical,Mystery,News,Reality-TV,Romance,Sci-Fi,Short,Sport,Talk-Show," +
      "Thriller,War,Western"))
  }
}

private case class Genre(genres: String)
