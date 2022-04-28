package dataframe.transformation

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class WithColumnRenamedTransformation extends AnyFlatSpec with Matchers {

  it should "rename a column" in {
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