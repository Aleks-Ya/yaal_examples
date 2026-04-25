package spark3.sql.dataframe.operation.transformation

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.sum
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}


class FlatMapTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "use flatMap transformation for parsing an array" in {
    import Factory.ss.implicits._
    val df = Factory.createDf("genre_array STRING",
        Row("Comedy,Drama,Action"),
        Row("Western,Sci-Fi"),
        Row("Horror"))
      .flatMap(row => {
        val genresStr = row.getString(row.fieldIndex("genre_array"))
        val genresArr = genresStr.split(",")
        genresArr
      })
      .toDF("genres")

    df shouldContain(
      """{"genres":"Comedy"}""",
      """{"genres":"Drama"}""",
      """{"genres":"Action"}""",
      """{"genres":"Western"}""",
      """{"genres":"Sci-Fi"}""",
      """{"genres":"Horror"}"""
    )
  }

  it should "convert ArrayType(IntegerType) to IntegerType" in {
    import Factory.ss.implicits._
    val numbersSum = Factory.createDf("numbers ARRAY<INT>",
        Row(Array(1, 2)),
        Row(Array(10, 20)))
      .flatMap(row => row.getSeq[Int](row.fieldIndex("numbers")))
      .agg(sum("value")).first().getLong(0)
    numbersSum shouldBe 33
  }

}