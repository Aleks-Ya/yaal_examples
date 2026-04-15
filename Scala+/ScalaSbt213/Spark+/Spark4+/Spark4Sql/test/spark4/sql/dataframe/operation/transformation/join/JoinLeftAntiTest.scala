package spark4.sql.dataframe.operation.transformation.join

import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory.createDf

class JoinLeftAntiTest extends AnyFlatSpec with Matchers {

  it should "perform a left anti join" in {
    val ddl = "id INT,city STRING"
    val allCitiesDf: DataFrame = createDf(ddl, Row(1, "London"), Row(2, "Berlin"), Row(3, "Paris"))
    val processedCitiesDf: DataFrame = createDf(ddl, Row(2, "Berlin"))
    val unprocessedCitiesDf: DataFrame = allCitiesDf.join(processedCitiesDf, "id", "left_anti")
    unprocessedCitiesDf.schema.toDDL shouldEqual ddl
    unprocessedCitiesDf.toJSON.collect should contain inOrderOnly(
      """{"id":1,"city":"London"}""",
      """{"id":3,"city":"Paris"}"""
    )
  }

}