package dataframe.show

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class VisualizeDfTest extends AnyFlatSpec with Matchers {

  it should "print some info" in {
    val df = Factory.peopleDf
    println("Table names:\n" + Factory.ss.sqlContext.tableNames.toList)

    println("Print Schema:")
    df.printSchema //-> peopleSchemaRdd.schema.treeString
    println("Show:")
    df.show

    println("Pretty JSON:\n" + df.schema.prettyJson)
    val tree = df.schema.treeString

    tree shouldEqual
      "root\n" +
        " |-- name: string (nullable = true)\n" +
        " |-- age: integer (nullable = true)\n" +
        " |-- gender: string (nullable = true)\n"

    println("Explain:\n")
    df.explain()

    println("\nExtended explain:\n")
    df.explain(extended = true)
  }

  it should "truncate long columns" in {
    val df = Factory.createDf(Map("text" -> StringType),
      Row("≤ 20 symbols"),
      Row("≥ 20 symbols symbols symbols")
    ).toDF("texts")
    df.toDF("truncated_text_1").show
    df.toDF("truncated_text_2").show(true)
    df.toDF("not_truncated_text").show(false)
  }

  it should "print tree string" in {
    val df = Factory.peopleDf
    val tree = df.schema.treeString
    tree shouldEqual
      "root\n" +
        " |-- name: string (nullable = true)\n" +
        " |-- age: integer (nullable = true)\n" +
        " |-- gender: string (nullable = true)\n"
    println(s"Tree String:\n$tree")
  }
}