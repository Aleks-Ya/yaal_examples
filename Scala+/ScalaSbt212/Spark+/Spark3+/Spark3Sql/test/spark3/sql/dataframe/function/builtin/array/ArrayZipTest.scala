package spark3.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{arrays_zip, col}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class ArrayZipTest extends AnyFlatSpec with SparkMatchers {

  it should "zip two arrays" in {
    val df = Factory.createDf("names ARRAY<STRING>, ages ARRAY<INT>",
      Row(Seq("John", "Mary"), Seq(30, 25)),
      Row(Seq("Mark", "Matt"), Seq(20)), // different length
      Row(Seq(), Seq()),
      Row(Seq(), null),
      Row(null, null))
    val updatedDf = df.withColumn("people", arrays_zip(col("names"), col("ages")))
    updatedDf shouldHaveDDL "names ARRAY<STRING>,ages ARRAY<INT>,people ARRAY<STRUCT<names: STRING, ages: INT>>"
    updatedDf shouldContain(
      """{"names":["John","Mary"],"ages":[30,25],"people":[{"names":"John","ages":30},{"names":"Mary","ages":25}]}""",
      """{"names":["Mark","Matt"],"ages":[20],"people":[{"names":"Mark","ages":20},{"names":"Matt","ages":null}]}""",
      """{"names":[],"ages":[],"people":[]}""",
      """{"names":[],"ages":null,"people":null}""",
      """{"names":null,"ages":null,"people":null}""")
  }

}