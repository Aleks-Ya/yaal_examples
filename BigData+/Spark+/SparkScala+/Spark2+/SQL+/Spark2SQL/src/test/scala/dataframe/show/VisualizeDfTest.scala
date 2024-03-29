package dataframe.show

import factory.Factory
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class VisualizeDfTest extends FlatSpec {

  "Visualize DF" should "print some info" in {
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
        " |-- age: integer (nullable = true)\n"

    println("Explain:\n")
    df.explain()

    println("\nExtended explain:\n")
    df.explain(extended = true)
  }
}