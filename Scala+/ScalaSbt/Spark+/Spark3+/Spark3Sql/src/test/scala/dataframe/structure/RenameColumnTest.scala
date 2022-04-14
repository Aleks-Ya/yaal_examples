package dataframe.structure

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class RenameColumnTest extends AnyFlatSpec with Matchers {

  "Rename columns" should "works" in {
    val df = Factory.peopleDf
    df.printSchema
    df.schema.treeString shouldEqual "root\n" +
      " |-- name: string (nullable = true)\n" +
      " |-- age: integer (nullable = true)\n" +
      " |-- gender: string (nullable = true)\n"

    val newDf = df.withColumnRenamed("name", "fio")
    newDf.printSchema
    newDf.schema.treeString shouldEqual
      "root\n" +
        " |-- fio: string (nullable = true)\n" +
        " |-- age: integer (nullable = true)\n" +
        " |-- gender: string (nullable = true)\n"
  }
}