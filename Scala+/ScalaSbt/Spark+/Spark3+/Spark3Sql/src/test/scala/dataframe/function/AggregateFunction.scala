package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{aggregate, col, lit}
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AggregateFunction extends AnyFlatSpec with Matchers {
  it should "calculate sum of array column" in {
    val df = Factory.createDf(Map("person" -> StringType, "sales" -> ArrayType(IntegerType)),
      Row("John", Seq(10, 20)),
      Row("Mary", Seq(1, 2)))
    val updatedDf = df.withColumn("sum", aggregate(col("sales"), lit(0), (x, y) => x + y))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"person":"John","sales":[10,20],"sum":30}""",
      """{"person":"Mary","sales":[1,2],"sum":3}"""
    )
  }

}