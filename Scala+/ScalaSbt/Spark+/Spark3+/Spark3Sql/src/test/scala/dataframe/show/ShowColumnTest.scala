package dataframe.show

import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class ShowColumnTest extends AnyFlatSpec with Matchers {

  it should "show a column" in {
    val ageCol = col("age")
    ageCol.toString() shouldEqual "age"

    val nonNullAgeCol = ageCol.isNotNull
    nonNullAgeCol.toString() shouldEqual "(age IS NOT NULL)"

    val doubleAgeCol = ageCol * 2
    doubleAgeCol.toString() shouldEqual "(age * 2)"
  }

  it should "show an expression of a column" in {
    val ageCol = col("age").isNotNull * 2
    ageCol.expr.toString shouldEqual "(isnotnull('age) * 2)"
    ageCol.expr.simpleString(1000) shouldEqual "(isnotnull('age) * 2)"
    ageCol.expr.sql shouldEqual "((age IS NOT NULL) * 2)"
  }

  it should "explain a column" in {
    val ageCol = col("age").isNotNull * 2
    ageCol.explain(false)
  }

}