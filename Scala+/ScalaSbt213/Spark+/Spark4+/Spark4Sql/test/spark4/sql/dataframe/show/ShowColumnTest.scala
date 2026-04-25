package spark4.sql.dataframe.show

import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.SparkMatchers


class ShowColumnTest extends AnyFlatSpec with SparkMatchers {

  it should "show a column" in {
    val ageCol = col("age")
    ageCol.toString() shouldEqual "age"

    val nonNullAgeCol = ageCol.isNotNull
    nonNullAgeCol.toString() shouldEqual "(age IS NOT NULL)"

    val doubleAgeCol = ageCol * 2
    doubleAgeCol.toString() shouldEqual "(age * 2)"
  }

  it should "explain a column" in {
    val ageCol = col("age").isNotNull * 2
    ageCol.explain(false)
    ageCol.explain(true)
  }

}