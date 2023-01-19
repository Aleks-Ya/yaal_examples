
package app.imdb

import org.apache.spark.sql.functions.col

/**
 * Calculate how many movies has the highest rating.
 */
object Exercise4App {
  def main(args: Array[String]): Unit = {
    val highestRating = DataFrameFactory.titleRatingsDf
      .select("averageRating")
      .distinct()
      .sort("averageRating")
      .first()
      .getDouble(0)

    val countMovies = DataFrameFactory.titleRatingsDf.count()

    val countMoviesWithHighersRating = DataFrameFactory.titleRatingsDf
      .filter(col("averageRating") === highestRating)
      .count()

    println(s"Highest rating: $highestRating")
    println(s"All movies: $countMovies")
    println(s"Movies with the highest rating: $countMoviesWithHighersRating")
    assert(highestRating == 1.0)
    assert(countMovies == 1238518)
    assert(countMoviesWithHighersRating == 1091)
  }
}
