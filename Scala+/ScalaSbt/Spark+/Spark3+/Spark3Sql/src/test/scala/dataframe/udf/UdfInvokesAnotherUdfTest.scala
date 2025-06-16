package dataframe.udf

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

//DOES NOT WORK
class UdfInvokesAnotherUdfTest extends AnyFlatSpec with Matchers {

  it should "UDF invokes other UDFs" in {
    val df = Factory.createDf("code STRING",
      Row("Big Vehicles"), Row("Small Vehicles"))

    val upperCaseUdf = udf((str: String) => str.toUpperCase)
    val removeSpacesUdf = udf((str: String) => str.replaceAll("\\s", "_"))
    val normalizeUdf = udf((str: String) => removeSpacesUdf(upperCaseUdf(lit(str))))

    val updatedDf = df.withColumn("normal", normalizeUdf(col("code")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":35,"upper_name":"JOHN"}""",
      """{"name":"Mary","age":20,"upper_name":"MARY"}"""
    )
  }

}