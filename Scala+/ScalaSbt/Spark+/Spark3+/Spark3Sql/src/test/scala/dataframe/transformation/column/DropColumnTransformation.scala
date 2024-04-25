package dataframe.transformation.column

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DropColumnTransformation extends AnyFlatSpec with Matchers {
  it should "delete a column" in {
    val df = Factory.peopleDf
    df.schema.simpleString shouldEqual "struct<name:string,age:int,gender:string>"
    val updatedDf = df.drop("gender")
    updatedDf.schema.simpleString shouldEqual "struct<name:string,age:int>"
  }
}