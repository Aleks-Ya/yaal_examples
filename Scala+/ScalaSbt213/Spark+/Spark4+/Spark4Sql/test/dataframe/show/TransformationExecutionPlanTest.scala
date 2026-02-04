package dataframe.show

import factory.Factory
import org.apache.spark.sql.functions.{col, collect_set}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class TransformationExecutionPlanTest extends AnyFlatSpec with Matchers {

  it should "show drop transformation" in {
    Factory.peopleDf.drop("age").queryExecution.simpleString shouldEqual
      """== Physical Plan ==
        |*(1) Project [name#3, gender#5]
        |+- *(1) Scan ExistingRDD[name#3,age#4,gender#5]
        |
        |""".stripMargin
  }

  it should "show select transformation" in {
    Factory.peopleDf.select(col("age")).queryExecution.simpleString shouldEqual
      """== Physical Plan ==
        |*(1) Project [age#4]
        |+- *(1) Scan ExistingRDD[name#3,age#4,gender#5]
        |
        |""".stripMargin
  }

  it should "show groupBy transformation" in {
    Factory.peopleDf.groupBy(col("age")).agg(col("age"), collect_set("name")).queryExecution.simpleString shouldEqual
      """== Physical Plan ==
        |AdaptiveSparkPlan isFinalPlan=false
        |+- ObjectHashAggregate(keys=[age#4], functions=[collect_set(name#3, 0, 0)])
        |   +- Exchange hashpartitioning(age#4, 200), ENSURE_REQUIREMENTS, [plan_id=39]
        |      +- ObjectHashAggregate(keys=[age#4], functions=[partial_collect_set(name#3, 0, 0)])
        |         +- Project [name#3, age#4]
        |            +- Scan ExistingRDD[name#3,age#4,gender#5]
        |
        |""".stripMargin
  }
}