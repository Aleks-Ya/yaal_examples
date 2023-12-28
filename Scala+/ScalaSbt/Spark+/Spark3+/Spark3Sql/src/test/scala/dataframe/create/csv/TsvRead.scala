package dataframe.create.csv

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.Objects.requireNonNull

class TsvRead extends AnyFlatSpec with Matchers {

  it should "read a TSV-file to a DataFrame" in {
    val file = requireNonNull(getClass.getResource("airports.tsv"))

    val df = Factory.ss.sqlContext.read
      .option("header", "true")
      .option("inferSchema", "true")
      .option("delimiter", "\t")
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

  it should "read a GZIP-compressed TSV-file to a DataFrame" in {
    val file = requireNonNull(getClass.getResource("airports.tsv.gz"))

    val df = Factory.ss.sqlContext.read
      .option("header", "true")
      .option("inferSchema", "true")
      .option("delimiter", "\t")
      .option("compression", "gzip")
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
}