package app.imdb

import org.apache.spark.sql.types._

object Schemas {
  val titleAkasSchema = new StructType(Array(
    StructField("titleId", StringType),
    StructField("ordering", IntegerType),
    StructField("title", StringType),
    StructField("region", StringType),
    StructField("language", StringType),
    StructField("types", StringType),
    StructField("attributes", StringType),
    StructField("isOriginalTitle", BooleanType)
  ))
  val titleBasicsSchema = new StructType(Array(
    StructField("tconst", StringType),
    StructField("titleType", StringType),
    StructField("primaryTitle", StringType),
    StructField("originalTitle", StringType),
    StructField("isAdult", BooleanType),
    StructField("startYear", IntegerType),
    StructField("endYear", IntegerType),
    StructField("runtimeMinutes", IntegerType),
    StructField("genres", StringType)
  ))
  val titleCrewSchema = new StructType(Array(
    StructField("tconst", StringType),
    StructField("directors", StringType),
    StructField("writers", StringType)
  ))
  val titleEpisodeSchema = new StructType(Array(
    StructField("tconst", StringType),
    StructField("parentTconst", StringType),
    StructField("seasonNumber", IntegerType),
    StructField("episodeNumber", IntegerType)
  ))
  val titlePrincipalsSchema = new StructType(Array(
    StructField("tconst", StringType),
    StructField("ordering", IntegerType),
    StructField("nconst", StringType),
    StructField("category", StringType),
    StructField("job", StringType),
    StructField("characters", StringType)
  ))
  val titleRatingsSchema = new StructType(Array(
    StructField("tconst", StringType),
    StructField("averageRating", DoubleType),
    StructField("numVotes", LongType)
  ))
  val nameBasicsSchema = new StructType(Array(
    StructField("nconst", StringType),
    StructField("primaryName", StringType),
    StructField("birthYear", IntegerType),
    StructField("deathYear", IntegerType),
    StructField("primaryProfession", StringType),
    StructField("knownForTitles", StringType)
  ))
}
