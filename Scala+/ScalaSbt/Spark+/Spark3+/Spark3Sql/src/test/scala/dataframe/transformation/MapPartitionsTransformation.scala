package dataframe.transformation

import factory.Factory
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapPartitionsTransformation extends AnyFlatSpec with Matchers {

  it should "map a DataFrame by partitions" in {
    val ds: Dataset[Row] = Factory.peopleDf.repartition(2)
    val df: DataFrame = ds
    implicit val encoder: Encoder[Row] = Encoders.row(df.schema)
    val mappedDs = df.mapPartitions(partition =>
      partition.map(row => {
        val name = row.getAs[String]("name")
        val age = row.getAs[Int]("age")
        val gender = row.getAs[String]("gender")
        Row(name.toUpperCase, age - 1, gender)
      }
      )
    )
    mappedDs.toJSON.collect() should contain inOrderOnly(
      """{"name":"JOHN","age":24,"gender":"M"}""",
      """{"name":"MARY","age":19,"gender":"F"}""",
      """{"name":"PETER","age":34,"gender":"M"}""")
  }

}
