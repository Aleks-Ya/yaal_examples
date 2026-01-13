package dataframe.udf.named.extension

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExtensionUdfWithNameTest extends AnyFlatSpec with Matchers {

  it should "give a meaningful name to a UDF (by class)" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val updatedDf = df.withColumn("upper_name", UpperCaseNamedExtensionUdf(col("name")))
    updatedDf.queryExecution.simpleString shouldEqual
      """== Physical Plan ==
        |*(1) Project [name#1, UpperCaseNamedExtensionUdf(name#1) AS upper_name#10]
        |+- *(1) Scan ExistingRDD[name#1]
        |
        |""".stripMargin
  }

  it should "give a meaningful name to a UDF (custom UDF name)" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val updatedDf = df.withColumn("upper_name", UpperCaseNamedExtensionCustomNameUdf(col("name")))
    updatedDf.queryExecution.simpleString shouldEqual
      """== Physical Plan ==
        |*(1) Project [name#1, MyUpperCaseUdf(name#1) AS upper_name#10]
        |+- *(1) Scan ExistingRDD[name#1]
        |
        |""".stripMargin
  }

}
