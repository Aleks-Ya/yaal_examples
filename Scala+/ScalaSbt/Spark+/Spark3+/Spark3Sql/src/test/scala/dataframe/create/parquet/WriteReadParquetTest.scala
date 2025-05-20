package dataframe.create.parquet

import factory.Factory
import factory.Factory.createDf
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Row, SaveMode}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

class WriteReadParquetTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {
  it should "write to parquet file" in {
    val originalDf = Factory.peopleDf

    val file = FileUtil.createAbsentTmpDirStr()
    originalDf.write.parquet(file)

    val sql = Factory.ss.sqlContext
    val parquetDf = sql.read.parquet(file)

    parquetDf.toJSON.collect() shouldEqual originalDf.toJSON.collect()
    parquetDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

  it should "read several parquet files in single DataFrame" in {
    val originalDf = Factory.peopleDf
    val maleDf = originalDf.filter(col("gender") === "M")
    maleDf.count() shouldEqual 2
    val femaleDf = originalDf.filter(col("gender") === "F")
    femaleDf.count() shouldEqual 1

    val maleFile = FileUtil.createAbsentTmpDirStr()
    val femaleFile = FileUtil.createAbsentTmpDirStr()
    maleDf.write.parquet(maleFile)
    femaleDf.write.parquet(femaleFile)

    val sql = Factory.ss.sqlContext
    val parquetDf = sql.read.parquet(maleFile, femaleFile)

    parquetDf.toJSON.collect() shouldEqual originalDf.toJSON.collect()
    parquetDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

  it should "append a parquet file" in {
    val sql = Factory.ss.sqlContext
    val schema = StructType.fromDDL("city string")
    val file = FileUtil.createAbsentTmpDirStr()

    val df1 = createDf(schema, Row("London"))
    df1.write.parquet(file)
    sql.read.parquet(file).toJSON.collect() should contain only """{"city":"London"}"""

    val df2 = createDf(schema, Row("Berlin"))
    df2.write.mode(SaveMode.Append).parquet(file)
    sql.read.parquet(file).toJSON.collect() should contain only(
      """{"city":"Berlin"}""",
      """{"city":"London"}"""
    )
  }
}