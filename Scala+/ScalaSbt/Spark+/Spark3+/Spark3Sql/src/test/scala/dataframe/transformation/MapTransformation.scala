package dataframe.transformation

import factory.Factory
import org.apache.spark.sql.functions.avg
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class MapTransformation extends AnyFlatSpec with Matchers {

  it should "use map transformation" in {
    import Factory.ss.sqlContext.implicits._
    val df = Factory.peopleDf
      .map(row => {
        val name = row.getString(row.fieldIndex("name"))
        val maturity = if (row.getInt(row.fieldIndex("age")) >= 18) "Adult" else "Adolescent"
        val gender = row.getString(row.fieldIndex("gender"))
        (name, maturity, gender)
      })
      .toDF("name", "maturity", "gender")

    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","maturity":"Adult","gender":"M"}""",
      """{"name":"Peter","maturity":"Adult","gender":"M"}""",
      """{"name":"Mary","maturity":"Adult","gender":"F"}"""
    )
  }

  it should "map to Dataset of primitives" in {
    import Factory.ss.sqlContext.implicits._
    val avgAge = Factory.peopleDf
      .map(row => row.getInt(row.fieldIndex("age")))
      .agg(avg("value"))
      .first()
      .getDouble(0)
    avgAge shouldBe 26.666666666666668
  }

}