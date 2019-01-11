package dataset

import factory.Factory
import org.apache.spark.sql.{Encoder, SparkSession}
import org.scalatest.{FlatSpec, Matchers}

class InCodeTest extends FlatSpec with Matchers {

  private val ss: SparkSession = Factory.ss

  it should "create Dataset from Seq v1" in {
    val exp = Seq("a", "b")
    import ss.sqlContext.implicits._
    val ds = ss.createDataset(exp)
    val act = ds.collect()
    act should contain allElementsOf exp
  }

  it should "init Dataset from Seq v2" in {
    import ss.sqlContext.implicits._
    val exp = Seq("c", "d")
    val ds = exp.toDS()
    val act = ds.collect()
    act should contain allElementsOf exp
  }

  it should "init DataSet with POJO" in {
    implicit val mapEncoder: Encoder[PeoplePojo] = org.apache.spark.sql.Encoders.kryo[PeoplePojo]
    val list = List(PeoplePojo("John", 25), PeoplePojo("Petr", 30))
    val people = Factory.ss.createDataset(list).collect()
    people should contain allElementsOf list
  }

}

private case class PeoplePojo(name: String, age: Int) {}
