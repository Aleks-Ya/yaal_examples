package spark4.sql.dataframe.transformation.column

import org.apache.spark.sql.DataFrame
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class WithColumnRenamedTest extends AnyFlatSpec with Matchers {

  it should "rename a column" in {
    val df: DataFrame = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf: DataFrame = df.withColumnRenamed("name", "fio")
    updatedDf.schema.toDDL shouldEqual "fio STRING,age INT,gender STRING"
  }

  ignore should "rename a nested column" in {
    // Dataset#withColumnRenamed supports only top-level columns.
    // See "rename a nested column" in UpdateColumnTest
  }

}