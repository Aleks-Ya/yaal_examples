package dataframe.create.csv

import factory.Factory
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.Objects.requireNonNull

class CsvRead extends AnyFlatSpec with Matchers {

  it should "read a CSV-file in a DataFrame" in {
    val file = requireNonNull(getClass.getResource("airports.csv"))

    val df = Factory.ss.sqlContext.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(file.getPath)

    df.schema.simpleString shouldEqual "struct<iata:string,airport:string,city:string,state:string,country:string,lat:double,long:double>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"iata":"00M","airport":"Thigpen ","city":"Bay Springs","state":"MS","country":"USA","lat":31.95376472,"long":-89.23450472}""",
      """{"iata":"00R","airport":"Livingston Municipal","city":"Livingston","state":"TX","country":"USA","lat":30.68586111,"long":-95.01792778}""",
      """{"iata":"00V","airport":"Meadow Lake","city":"Colorado Springs","state":"CO","country":"USA","lat":38.94574889,"long":-104.5698933}""",
      """{"iata":"01G","airport":"Perry-Warsaw","city":"Perry","state":"NY","country":"USA","lat":42.74134667,"long":-78.05208056}""",
      """{"iata":"01J","airport":"Hilliard Airpark","city":"Hilliard","state":"FL","country":"USA","lat":30.6880125,"long":-81.90594389}"""
    )
  }

  it should "use custom null value in a CSV file" in {
    val file = requireNonNull(getClass.getResource("custom_null.csv"))

    val df = Factory.ss.sqlContext.read
      .option("header", "true")
      .option("inferSchema", "true")
      .option("nullValue", "\\N")
      .csv(file.getPath)

    df.schema.simpleString shouldEqual "struct<airport:string,city:string>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"airport":"Thigpen ","city":"Bay Springs"}""",
      """{"airport":"Livingston Municipal","city":null}""",
      """{"airport":"Meadow Lake","city":"Colorado Springs"}"""
    )
  }

  it should "use custom schema for a CSV file" in {
    val file = requireNonNull(getClass.getResource("people.csv"))

    val idField = StructField("id", LongType)
    val nameField = StructField("name", StringType)
    val ageField = StructField("age", IntegerType)
    val effectivenessField = StructField("effectiveness", DoubleType)
    val schema = StructType(idField :: nameField :: ageField :: effectivenessField :: Nil)

    val df = Factory.ss.sqlContext.read
      .option("header", "true")
      .option("inferSchema", "false")
      .schema(schema)
      .csv(file.getPath)

    df.schema.simpleString shouldEqual "struct<id:bigint,name:string,age:int,effectiveness:double>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"id":1,"name":"John","age":35,"effectiveness":75.5}""",
      """{"id":2,"name":"Mary","age":25,"effectiveness":66.1}"""
    )
  }

}