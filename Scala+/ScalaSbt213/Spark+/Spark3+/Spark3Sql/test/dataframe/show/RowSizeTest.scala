package dataframe.show

import factory.Factory
import org.apache.spark.sql.functions
import org.apache.spark.sql.functions.{col, struct, to_json, udf}
import org.apache.spark.util.SizeEstimator
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class RowSizeTest extends AnyFlatSpec with Matchers {
  it should "calculate row size in bytes (JSON)" in {
    val df = Factory.peopleDf
    val updatedDf = df.withColumn("size", functions.length(to_json(struct(df.columns.map(col): _*))))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","size":37}""",
      """{"name":"Peter","age":35,"gender":"M","size":38}""",
      """{"name":"Mary","age":20,"gender":"F","size":37}""")
  }

  it should "calculate row size in bytes (SizeEstimator)" in {
    val df = Factory.peopleDf
    val sizeUdf = udf(row => SizeEstimator.estimate(row))
    val updatedDf = df.withColumn("size", sizeUdf(struct(df.columns.map(col): _*)))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","size":560}""",
      """{"name":"Peter","age":35,"gender":"M","size":568}""",
      """{"name":"Mary","age":20,"gender":"F","size":560}""")
  }
}