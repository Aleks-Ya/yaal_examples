package dataframe.transformation.join

import factory.Factory
import org.apache.spark.sql.Row
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JoinLeftAntiTest extends AnyFlatSpec with Matchers {

  it should "perform a left anti join" in {
    val ddl = "id INT,city STRING"
    val allCitiesDf = Factory.createDf(ddl, Row(1, "London"), Row(2, "Berlin"), Row(3, "Paris"))
    val processedCitiesDf = Factory.createDf(ddl, Row(2, "Berlin"))
    val unprocessedCitiesDf = allCitiesDf.join(processedCitiesDf, "id", "left_anti")
    unprocessedCitiesDf.schema.toDDL shouldEqual ddl
    unprocessedCitiesDf.toJSON.collect should contain inOrderOnly(
      """{"id":1,"city":"London"}""",
      """{"id":3,"city":"Paris"}"""
    )
  }

}