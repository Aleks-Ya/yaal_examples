package dataframe.udf.named.`trait`

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TraitUdfWithNameTest extends AnyFlatSpec with Matchers {

  it should "give a meaningful name to a UDF" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val updatedDf = df.withColumn("upper_name", UpperCaseNamedTraitUdf(col("name")))
    updatedDf.queryExecution.simpleString shouldEqual
      """== Physical Plan ==
        |*(1) Project [name#1, UpperCaseNamedTraitUdf(name#1) AS upper_name#10]
        |+- *(1) Scan ExistingRDD[name#1]
        |
        |""".stripMargin
  }

}
