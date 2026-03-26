package spark4.sql.dataframe.transformation.column

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class WithColumnRenamedTest extends AnyFlatSpec with Matchers {
  it should "rename a column" in {
    val df = Factory.peopleDf
    df.schema.simpleString shouldEqual "struct<name:string,age:int,gender:string>"
    val updatedDf = df.withColumnRenamed("name", "fio")
    updatedDf.schema.simpleString shouldEqual "struct<fio:string,age:int,gender:string>"
  }
}