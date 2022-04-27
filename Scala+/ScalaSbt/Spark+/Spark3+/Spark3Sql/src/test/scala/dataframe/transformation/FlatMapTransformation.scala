package dataframe.transformation

import factory.Factory
import factory.Factory.ss
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class FlatMapTransformation extends AnyFlatSpec with Matchers {

  it should "use flatMap transformation for parsing an array" in {
    val schema = StructType(StructField("genre_array", StringType, nullable = true) :: Nil)
    val rdd = ss.sparkContext.parallelize(Seq("Comedy,Drama,Action", "Western,Sci-Fi", "Horror")).map(Row(_))
    import Factory.ss.sqlContext.implicits._
    val df = ss.sqlContext.createDataFrame(rdd, schema)
      .flatMap(row => {
        val genresStr = row.getString(row.fieldIndex("genre_array"))
        val genresArr = genresStr.split(",")
        genresArr
      })
      .toDF("genres")

    df.toJSON.collect() should contain inOrderOnly(
      """{"genres":"Comedy"}""",
      """{"genres":"Drama"}""",
      """{"genres":"Action"}""",
      """{"genres":"Western"}""",
      """{"genres":"Sci-Fi"}""",
      """{"genres":"Horror"}"""
    )
  }

}