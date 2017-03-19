package dataframe.structure

import dataframe.DfFactory
import org.scalatest.{FlatSpec, Matchers}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.ByteType

class ChangeColumnTypeTest extends FlatSpec with Matchers {

  "Cast to related type" should "works" in {
    val df = DfFactory.peopleDf
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
    val df = DfFactory.peopleDf
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