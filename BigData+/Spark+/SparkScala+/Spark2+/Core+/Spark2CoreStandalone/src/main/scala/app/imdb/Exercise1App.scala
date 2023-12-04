
package app.imdb

/**
 * Print dataset schemas: 1) Dataset name, 2) Schema, 3) Show.
 */
object Exercise1App {
  def main(args: Array[String]): Unit = {
    printInfo(ImdbTable.titleAkas, "struct<titleId:string,ordering:int,title:string,region:string,language:string,types:string,attributes:string,isOriginalTitle:boolean>")
    printInfo(ImdbTable.titleBasics, "struct<tconst:string,titleType:string,primaryTitle:string,originalTitle:string,isAdult:boolean,startYear:int,endYear:int,runtimeMinutes:int,genres:string>")
    printInfo(ImdbTable.titleCrew, "struct<tconst:string,directors:string,writers:string>")
    printInfo(ImdbTable.titleEpisode, "struct<tconst:string,parentTconst:string,seasonNumber:int,episodeNumber:int>")
    printInfo(ImdbTable.titlePrincipals, "struct<tconst:string,ordering:int,nconst:string,category:string,job:string,characters:string>")
    printInfo(ImdbTable.titleRatings, "struct<tconst:string,averageRating:double,numVotes:bigint>")
    printInfo(ImdbTable.nameBasics, "struct<nconst:string,primaryName:string,birthYear:int,deathYear:int,primaryProfession:string,knownForTitles:string>")
  }

  private def printInfo(imdbDataFrame: ImdbTable.Category, expSchema: String): Unit = {
    println("--------------------------------------------------------------")
    println(s"Table: ${imdbDataFrame.toString}")
    val df = DataFrameFactory.datasetByName(imdbDataFrame)
    df.printSchema()
    println(s"Schema simple string: ${df.schema.simpleString}")
    df.show()
    assert(df.schema.simpleString.equals(expSchema))
  }
}
