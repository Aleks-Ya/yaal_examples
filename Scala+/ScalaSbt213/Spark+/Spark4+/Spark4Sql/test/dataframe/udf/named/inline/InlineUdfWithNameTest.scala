package dataframe.udf.named.inline

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InlineUdfWithNameTest extends AnyFlatSpec with Matchers {

  it should "give a meaningful name to a UDF" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val upperCaseUdf = udf((str: String) => str.toUpperCase).withName("UPPER_CASE")
    val updatedDf = df.withColumn("upper_name", upperCaseUdf(col("name")))
    updatedDf.queryExecution.simpleString shouldEqual
      """== Physical Plan ==
        |*(1) Project [name#1, UPPER_CASE(name#1) AS upper_name#10]
        |+- *(1) Scan ExistingRDD[name#1]
        |
        |""".stripMargin
  }

}