package spark4.sql.dataframe.operation.transformation.aggregation

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class GroupByTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "groupBy a string column" in {
    val df = Factory.peopleDf
    val updatedDf = df.groupBy("gender").count()
    updatedDf shouldHaveDDL "gender STRING,count BIGINT NOT NULL"
    updatedDf shouldContain(
      """{"gender":"F","count":1}""",
      """{"gender":"M","count":2}"""
    )
  }

  it should "groupBy with agg" in {
    val ageCol = "age"
    val df = Factory.peopleDf
    val updatedDf = df.groupBy("gender").agg(
      max(ageCol) as "max_age",
      avg(ageCol) as "avg_age",
      min(ageCol) as "min_age")
    updatedDf shouldHaveDDL "gender STRING,max_age INT,avg_age DOUBLE,min_age INT"
    updatedDf shouldContain(
      """{"gender":"F","max_age":20,"avg_age":20.0,"min_age":20}""",
      """{"gender":"M","max_age":35,"avg_age":30.0,"min_age":25}"""
    )
  }

  it should "groupBy by several columns" in {
    val df = Factory.createDf("name STRING, age INT, gender STRING",
      Row("John", 30, "M"),
      Row("Mary", 30, "F"),
      Row("Mark", 25, "M"),
      Row("Chad", 30, "M"))
    val updatedDf = df.groupBy("gender", "age").agg(collect_list("name") as "names")
    updatedDf shouldHaveDDL "gender STRING,age INT,names ARRAY<STRING> NOT NULL"
    updatedDf shouldContain(
      """{"gender":"M","age":25,"names":["Mark"]}""",
      """{"gender":"M","age":30,"names":["John","Chad"]}""",
      """{"gender":"F","age":30,"names":["Mary"]}"""
    )
  }

  it should "group by an array column" in {
    val df = Factory.createDf("person STRING, countries ARRAY<STRING>",
      Row("John", Seq("USA", "Germany", "UK")),
      Row("Mary", Seq("Belgium", "Canada", "Australia")),
      Row("Mark", Seq("USA", "Germany", "UK")),
      Row("Chad", Seq("Germany", "USA", "UK"))
    )

    val unsortedArrayDf = df.groupBy("countries").agg(
      collect_list("person").as("persons")
    )
    unsortedArrayDf shouldHaveDDL "countries ARRAY<STRING>,persons ARRAY<STRING> NOT NULL"
    unsortedArrayDf.toJSON.collect should contain inOrderOnly(
      """{"countries":["USA","Germany","UK"],"persons":["John","Mark"]}""",
      """{"countries":["Belgium","Canada","Australia"],"persons":["Mary"]}""",
      """{"countries":["Germany","USA","UK"],"persons":["Chad"]}"""
    )

    val sortedArrayDf = df.groupBy(sort_array(col("countries")).as("countries")).agg(
      collect_list("person").as("persons")
    )
    sortedArrayDf shouldHaveDDL "countries ARRAY<STRING>,persons ARRAY<STRING> NOT NULL"
    sortedArrayDf.toJSON.collect should contain inOrderOnly(
      """{"countries":["Germany","UK","USA"],"persons":["John","Mark","Chad"]}""",
      """{"countries":["Australia","Belgium","Canada"],"persons":["Mary"]}"""
    )
  }

  it should "groupBy by a struct column" in {
    val df = Factory.createDf("name STRING, details STRUCT<age INT, gender STRING>",
      Row("John", Row(30, "M")),
      Row("Mary", Row(30, "F")),
      Row("Mark", Row(25, "M")),
      Row("Chad", Row(30, "M")))
    val updatedDf = df.groupBy("details").agg(collect_list("name") as "names")
    updatedDf shouldHaveDDL "details STRUCT<age: INT, gender: STRING>,names ARRAY<STRING> NOT NULL"
    updatedDf shouldContain(
      """{"details":{"age":30,"gender":"F"},"names":["Mary"]}""",
      """{"details":{"age":30,"gender":"M"},"names":["John","Chad"]}""",
      """{"details":{"age":25,"gender":"M"},"names":["Mark"]}"""
    )
  }

  it should "show elements count in each group" in {
    val df = Factory.peopleDf
    val updatedDf: DataFrame = df.groupBy("gender").count
    updatedDf shouldHaveDDL "gender STRING,count BIGINT NOT NULL"
    updatedDf shouldContain(
      """{"gender":"F","count":1}""",
      """{"gender":"M","count":2}"""
    )
  }

  it should "show groups count (by groupBy)" in {
    val df = Factory.peopleDf
    val groupsCount: Long = df.groupBy("gender").count.count
    groupsCount shouldEqual 2
  }

  it should "show groups count (by distinct)" in {
    val df = Factory.peopleDf
    val groupsCount: Long = df.select("gender").distinct.count
    groupsCount shouldEqual 2
  }

}