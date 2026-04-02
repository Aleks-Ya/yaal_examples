package spark3.sql.dataframe.compare

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.IntegerType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory
import spark3.sql.Factory.createDf

class AssertDataFrameSchemaTest extends AnyFlatSpec with Matchers {

  it should "assert a field data type of a DataFrame" in {
    val df = Factory.peopleDf
    df.schema.fields(df.schema.fieldIndex("age")).dataType shouldBe IntegerType
    df.schema.find(_.name.equals("age")).get.dataType shouldBe IntegerType
  }

  it should "assert a whole schema (DDL)" in {
    Factory.peopleDf.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
  }

  it should "assert a whole schema (simpleString)" in {
    Factory.peopleDf.schema.simpleString shouldEqual "struct<name:string,age:int,gender:string>"
  }

  it should "assert a whole schema (catalogString)" in {
    Factory.peopleDf.schema.catalogString shouldEqual "struct<name:string,age:int,gender:string>"
  }

  it should "assert a whole schema (JSON)" in {
    Factory.peopleDf.schema.json shouldEqual """{"type":"struct","fields":[{"name":"name","type":"string","nullable":true,"metadata":{}},{"name":"age","type":"integer","nullable":true,"metadata":{}},{"name":"gender","type":"string","nullable":true,"metadata":{}}]}"""
  }

  it should "assert a whole schema (mkString)" in {
    Factory.peopleDf.schema.mkString shouldEqual "StructField(name,StringType,true)StructField(age,IntegerType,true)StructField(gender,StringType,true)"
  }

  it should "assert a whole schema (tree string)" in {
    Factory.peopleDf.schema.treeString shouldEqual "root\n" +
      " |-- name: string (nullable = true)\n" +
      " |-- age: integer (nullable = true)\n" +
      " |-- gender: string (nullable = true)\n"
  }

  it should "assert schema ignoring column order (top-level only)" in {
    val df1 = createDf("id INT, name STRING", Row(1, "John"))
    val df2 = createDf("name STRING, id INT", Row("Mary", 2))
    val df3 = createDf("id INT, person STRING", Row(3, "Mark"))

    val equal12 = df1.schema.toSet == df2.schema.toSet
    equal12 shouldBe true
    val diff12 = df1.schema.diff(df2.schema)
    diff12 shouldBe empty

    val equal23 = df2.schema.toSet == df3.schema.toSet
    equal23 shouldBe false
    val diff23 = df2.schema.diff(df3.schema) union df3.schema.diff(df2.schema)
    diff23.map(_.name) should contain only("name", "person")
  }

}