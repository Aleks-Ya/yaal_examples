package dataframe.create.csv

import factory.Factory
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CsvRead extends AnyFlatSpec with Matchers {

  it should "read a CSV-file in a DataFrame" in {
    val file = getClass.getResource("airports.csv")
    file should not be null

    val df = Factory.ss.sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(file.getPath)

    df.printSchema()
    df.show()
  }

  it should "use custom null value in a CSV file" in {
    val file = getClass.getResource("custom_null.csv")
    file should not be null

    val df = Factory.ss.sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .option("nullValue", "\\N")
      .load(file.getPath)

    df.printSchema()
    df.show()

    val row = df.filter(col("airport") === "Livingston Municipal").first()
    row.toSeq shouldBe Seq("Livingston Municipal", null)
  }

  it should "use custom schema for a CSV file" in {
    val file = getClass.getResource("people.csv")
    file should not be null

    val idField = StructField("id", LongType, nullable = true)
    val nameField = StructField("name", StringType, nullable = true)
    val ageField = StructField("age", IntegerType, nullable = true)
    val effectivenessField = StructField("effectiveness", DoubleType, nullable = true)
    val schema = StructType(idField :: nameField :: ageField :: effectivenessField :: Nil)

    val df = Factory.ss.sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "false")
      .schema(schema)
      .load(file.getPath)

    df.printSchema()
    df.show()
    df.toJSON.collect() should contain inOrderOnly(
      """{"id":1,"name":"John","age":35,"effectiveness":75.5}""",
      """{"id":2,"name":"Mary","age":25,"effectiveness":66.1}"""
    )
  }

}