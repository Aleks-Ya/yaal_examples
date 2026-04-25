package spark3.sql.dataset.udf

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
 * UDF as a variable.
 */
class UdfVariableTest extends AnyFlatSpec with SparkMatchers {

  it should "init dataset" in {
    import Factory.ss.implicits._
    val upper: String => String = _.toUpperCase
    val upperUdf = udf(upper)
    val df = Factory.ss
      .createDataset(Seq("a", "b"))
      .withColumn("upper", upperUdf($"value"))
    df shouldContain(
      """{"value":"a","upper":"A"}""",
      """{"value":"b","upper":"B"}"""
    )
  }

  it should "two arguments UDF" in {
    import Factory.ss.implicits._
    val upper: (String, Int) => String = (name: String, age: Int) => s"${name.toUpperCase}-$age"
    val upperUdf = udf(upper: (String, Int) => String)
    val df = Factory.peopleDf.withColumn("upper", upperUdf($"name", $"age"))
    df shouldContain(
      """{"name":"John","age":25,"gender":"M","upper":"JOHN-25"}""",
      """{"name":"Peter","age":35,"gender":"M","upper":"PETER-35"}""",
      """{"name":"Mary","age":20,"gender":"F","upper":"MARY-20"}"""
    )
  }

  it should "put sum of an array to new column" in {
    import Factory.ss.implicits._
    val ds = Factory.ss.createDataset(Seq(Array(1, 2, 3), Array(4, 5, 6)))
    val sum: Seq[Int] => Int = _.sum
    sum(Array(1, 2, 3)) shouldEqual 6
    val sumUdf = udf(sum)
    val newDs = ds.withColumn("sum", sumUdf($"value"))
    newDs.show
    val dsSum: Dataset[Int] = newDs.select(col("sum").as[Int])
    val list: mutable.Buffer[Int] = dsSum.collectAsList().asScala
    list should contain inOrderOnly(6, 15)
  }

  it should "pass additional params to UDF" in {
    import Factory.ss.implicits._
    val upperSuffix: String => String => String = suffix => string => (string + suffix).toUpperCase
    val upperUdf = udf(upperSuffix("_suf"))
    val df = Factory.ss
      .createDataset(Seq("a", "b"))
      .withColumn("upper", upperUdf($"value"))
    df shouldContain(
      """{"value":"a","upper":"A_SUF"}""",
      """{"value":"b","upper":"B_SUF"}"""
    )
  }

  it should "one UDF column uses another UDF column" in {
    import Factory.ss.implicits._
    val addSuffix: String => String = _ + "_suf"
    val addSuffixUdf = udf(addSuffix)
    val upper: String => String = _.toUpperCase
    val upperUdf = udf(upper)
    val df = Factory.ss
      .createDataset(Seq("a", "b"))
      .withColumn("suffix", addSuffixUdf($"value"))
      .withColumn("upper", upperUdf($"suffix"))
    df shouldContain(
      """{"value":"a","suffix":"a_suf","upper":"A_SUF"}""",
      """{"value":"b","suffix":"b_suf","upper":"B_SUF"}"""
    )
  }

}