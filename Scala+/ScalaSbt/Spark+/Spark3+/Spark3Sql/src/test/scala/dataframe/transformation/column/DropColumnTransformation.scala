package dataframe.transformation.column

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DropColumnTransformation extends AnyFlatSpec with Matchers {

  it should "delete a column" in {
    val df = Factory.peopleDf.drop("gender")
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25}""",
      """{"name":"Peter","age":35}""",
      """{"name":"Mary","age":20}"""
    )
  }

}