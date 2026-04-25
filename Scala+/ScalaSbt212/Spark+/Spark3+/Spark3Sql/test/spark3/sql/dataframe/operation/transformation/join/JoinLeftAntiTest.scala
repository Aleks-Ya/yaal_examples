package spark3.sql.dataframe.operation.transformation.join

import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class JoinLeftAntiTest extends AnyFlatSpec with SparkMatchers {

  it should "perform a left anti join" in {
    val ddl = "id INT,city STRING"
    val allCitiesDf: DataFrame = Factory.createDf(ddl, Row(1, "London"), Row(2, "Berlin"), Row(3, "Paris"))
    val processedCitiesDf: DataFrame = Factory.createDf(ddl, Row(2, "Berlin"))
    val unprocessedCitiesDf: DataFrame = allCitiesDf.join(processedCitiesDf, "id", "left_anti")
    unprocessedCitiesDf shouldHaveDDL ddl
    unprocessedCitiesDf shouldContain(
      """{"id":1,"city":"London"}""",
      """{"id":3,"city":"Paris"}"""
    )
  }

}