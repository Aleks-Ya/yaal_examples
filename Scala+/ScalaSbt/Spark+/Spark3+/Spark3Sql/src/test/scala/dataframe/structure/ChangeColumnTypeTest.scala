package dataframe.structure

import factory.Factory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.ByteType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ChangeColumnTypeTest extends AnyFlatSpec with Matchers {

  "Cast to related type" should "works" in {
    val df = Factory.peopleDf
    df.printSchema
    df.schema.treeString shouldEqual "root\n" +
      " |-- name: string (nullable = true)\n" +
      " |-- age: integer (nullable = true)\n"

    val newDf = df.withColumn("age", col("age").cast(ByteType))
    newDf.printSchema
    newDf.schema.treeString shouldEqual
      "root\n" +
        " |-- name: string (nullable = true)\n" +
        " |-- age: byte (nullable = true)\n"
  }

  "Cast with conversion" should "works" in {
    val df = Factory.peopleDf
    df.printSchema
    df.schema.treeString shouldEqual "root\n" +
      " |-- name: string (nullable = true)\n" +
      " |-- age: integer (nullable = true)\n"

    val newDf = df.withColumn("age", col("age").cast(ByteType))
    newDf.printSchema
    newDf.schema.treeString shouldEqual
      "root\n" +
        " |-- name: string (nullable = true)\n" +
        " |-- age: byte (nullable = true)\n"
  }
}