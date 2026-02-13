package app.imdb

object ImdbTable extends Enumeration {
  type Category = Value
  val titleAkas, titleBasics, titleCrew, titleEpisode, titlePrincipals, titleRatings, nameBasics = Value
}
