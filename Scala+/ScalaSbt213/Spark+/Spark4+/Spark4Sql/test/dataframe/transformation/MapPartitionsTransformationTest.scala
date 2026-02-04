package dataframe.transformation

import factory.Factory
import factory.Factory.ss
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, Row, SaveMode, SparkSession}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

class MapPartitionsTransformationTest extends AnyFlatSpec with Matchers {

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
      })
    )
    mappedDs.toJSON.collect should contain inOrderOnly(
      """{"name":"JOHN","age":24,"gender":"M"}""",
      """{"name":"MARY","age":19,"gender":"F"}""",
      """{"name":"PETER","age":34,"gender":"M"}""")
  }

  it should "return empty Iterator" in {
    val df: DataFrame = Factory.peopleDf.repartition(2)
    implicit val encoder: Encoder[Row] = Encoders.row(df.schema)
    val mappedDs = df.mapPartitions(_ => Iterator.empty)
    mappedDs.toJSON.collect shouldBe empty
  }

  //NOT WORK
  ignore should "append Parquet by partitions" in {
    val schema = StructType.fromDDL("city string")
    val rows = Seq(Row("London"), Row("Berlin"), Row("Paris"))
    val partitionNumber = 2
    val df = Factory.ss.createDataFrame(ss.sparkContext.parallelize(rows, partitionNumber), schema)
    val file = FileUtil.createAbsentTmpDirStr()
    implicit val encoder: Encoder[Row] = Encoders.row(df.schema)
    val updatedDf = df.mapPartitions(partition => {
      val rows = partition.map(row => {
        Row(row.getString(0).toUpperCase())
      }).toSeq
      val spark = SparkSession.builder().getOrCreate()
      val rdd = spark.sparkContext.parallelize(rows)
      val rowsDf = spark.createDataFrame(rdd, schema)
      rowsDf.write.mode(SaveMode.Append).parquet(file)
      val res: Iterator[Row] = Iterator.empty
      res
    })
    updatedDf.collect()
  }

}
