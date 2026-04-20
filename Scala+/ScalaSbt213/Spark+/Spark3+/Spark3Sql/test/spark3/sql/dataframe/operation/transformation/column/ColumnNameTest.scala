package spark3.sql.dataframe.operation.transformation.column

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class ColumnNameTest extends AnyFlatSpec with Matchers {

  it should "treat column names case-insensitive" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val df2 = df.select("GENDER", "NAME", "Age")
    df2.toJSON.collect should contain inOrderOnly(
      """{"GENDER":"M","NAME":"John","Age":25}""",
      """{"GENDER":"M","NAME":"Peter","Age":35}""",
      """{"GENDER":"F","NAME":"Mary","Age":20}"""
    )
  }

}