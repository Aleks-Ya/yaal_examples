package app.imdb

import app.imdb.Schemas._
import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFrameFactory {
  private val format = "com.databricks.spark.csv"
  private val options = Map("header" -> "true", "inferSchema" -> "false", "delimiter" -> "\t", "compression" -> "gzip",
    "nullValue" -> "\\N")
  private val baseDir = "/datasets/ImdbDataset/"

  lazy val ss: SparkSession = {
    val builder = SparkSession.builder().appName("IMDB")

    val logDir = sys.env.get("SPARK_HISTORY_FS_LOG_DIRECTORY")
    if (logDir.isDefined) {
      builder
        .config("spark.eventLog.enabled", "true")
        .config("spark.eventLog.dir", logDir.get)
    }

    builder.getOrCreate()
  }

  lazy val titleAkasDf: DataFrame = ss.read.format(format).options(options).schema(titleAkasSchema)
    .load(baseDir + "title.akas.tsv.gz")
  lazy val titleBasicsDf: DataFrame = ss.read.format(format).options(options).schema(titleBasicsSchema)
    .load(baseDir + "title.basics.tsv.gz")
  lazy val titleCrewDf: DataFrame = ss.read.format(format).options(options).schema(titleCrewSchema)
    .load(baseDir + "title.crew.tsv.gz")
  lazy val titleEpisodeDf: DataFrame = ss.read.format(format).options(options).schema(titleEpisodeSchema)
    .load(baseDir + "title.episode.tsv.gz")
  lazy val titlePrincipalsDf: DataFrame = ss.read.format(format).options(options).schema(titlePrincipalsSchema)
    .load(baseDir + "title.principals.tsv.gz")
  lazy val titleRatingsDf: DataFrame = ss.read.format(format).options(options).schema(titleRatingsSchema)
    .load(baseDir + "title.ratings.tsv.gz")
  lazy val nameBasicsDf: DataFrame = ss.read.format(format).options(options).schema(nameBasicsSchema)
    .load(baseDir + "name.basics.tsv.gz")

  def datasetByName(imdbTable: ImdbTable.Category): DataFrame = {
    imdbTable match {
      case ImdbTable.titleAkas => titleAkasDf
      case ImdbTable.titleBasics => titleBasicsDf
      case ImdbTable.titleCrew => titleCrewDf
      case ImdbTable.titleEpisode => titleEpisodeDf
      case ImdbTable.titlePrincipals => titlePrincipalsDf
      case ImdbTable.titleRatings => titleRatingsDf
      case ImdbTable.nameBasics => nameBasicsDf
      case _ => throw new IllegalArgumentException(s"Unsupported data frame: $imdbTable")
    }
  }

}
