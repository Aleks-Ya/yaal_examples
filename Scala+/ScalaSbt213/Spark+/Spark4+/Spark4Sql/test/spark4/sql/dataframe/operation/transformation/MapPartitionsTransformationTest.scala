package spark4.sql.dataframe.operation.transformation

import _root_.util.FileUtil
import org.apache.spark.sql._
import org.apache.spark.sql.types.StructType
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class MapPartitionsTransformationTest extends AnyFlatSpec with SparkMatchers {

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
    mappedDs shouldContain(
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
    val df = Factory.ss.createDataFrame(Factory.ss.sparkContext.parallelize(rows, partitionNumber), schema)
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
