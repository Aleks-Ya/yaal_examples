package dataframe.show

import factory.Factory
import org.apache.spark.sql.execution.{ExtendedMode, SimpleMode}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class QueryExecutionTest extends AnyFlatSpec with Matchers {

  it should "get execution plan as a string" in {
    val df = Factory.peopleDf.drop("age")
    val plan = df.queryExecution.optimizedPlan.toString()
    plan shouldEqual
      """== Parsed Logical Plan ==
        |Project [name#3, gender#5]
        |+- LogicalRDD [name#3, age#4, gender#5], false
        |
        |== Analyzed Logical Plan ==
        |name: string, gender: string
        |Project [name#3, gender#5]
        |+- LogicalRDD [name#3, age#4, gender#5], false
        |
        |== Optimized Logical Plan ==
        |Project [name#3, gender#5]
        |+- LogicalRDD [name#3, age#4, gender#5], false
        |
        |== Physical Plan ==
        |*(1) Project [name#3, gender#5]
        |+- *(1) Scan ExistingRDD[name#3,age#4,gender#5]
        |""".stripMargin
  }

  it should "get simple string" in {
    val df = Factory.peopleDf.drop("age")
    val plan = df.queryExecution.simpleString
    plan shouldEqual
      """== Physical Plan ==
        |*(1) Project [name#3, gender#5]
        |+- *(1) Scan ExistingRDD[name#3,age#4,gender#5]
        |
        |""".stripMargin
  }

  it should "get plan with statistics" in {
    val df = Factory.peopleDf.drop("age")
    val plan = df.queryExecution.stringWithStats
    plan shouldEqual
      """== Optimized Logical Plan ==
        |Project [name#3, gender#5], Statistics(sizeInBytes=7.4 EiB)
        |+- LogicalRDD [name#3, age#4, gender#5], false, Statistics(sizeInBytes=8.0 EiB)
        |
        |== Physical Plan ==
        |*(1) Project [name#3, gender#5]
        |+- *(1) Scan ExistingRDD[name#3,age#4,gender#5]
        |
        |""".stripMargin
  }

  it should "get optimized plan (logical plan)" in {
    val df = Factory.peopleDf.drop("age")
    val logicalPlan = df.queryExecution.optimizedPlan
    logicalPlan.toString shouldEqual
      """Project [name#3, gender#5]
        |+- LogicalRDD [name#3, age#4, gender#5], false
        |""".stripMargin
    logicalPlan.simpleString(100) shouldEqual """Project [name#3, gender#5]"""
  }

  it should "get an explain string" in {
    val df = Factory.peopleDf.drop("age")
    val explainSimple = df.queryExecution.explainString(SimpleMode)
    explainSimple shouldEqual
      """== Physical Plan ==
        |*(1) Project [name#3, gender#5]
        |+- *(1) Scan ExistingRDD[name#3,age#4,gender#5]
        |
        |""".stripMargin
    val explainExtended = df.queryExecution.explainString(ExtendedMode)
    explainExtended shouldEqual
      """== Parsed Logical Plan ==
        |Project [name#3, gender#5]
        |+- LogicalRDD [name#3, age#4, gender#5], false
        |
        |== Analyzed Logical Plan ==
        |name: string, gender: string
        |Project [name#3, gender#5]
        |+- LogicalRDD [name#3, age#4, gender#5], false
        |
        |== Optimized Logical Plan ==
        |Project [name#3, gender#5]
        |+- LogicalRDD [name#3, age#4, gender#5], false
        |
        |== Physical Plan ==
        |*(1) Project [name#3, gender#5]
        |+- *(1) Scan ExistingRDD[name#3,age#4,gender#5]
        |""".stripMargin
  }

}