package dataset.create

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InCodeTest extends AnyFlatSpec with Matchers {

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

  it should "init DataSet with POJO (kryo endocer)" in {
    implicit val mapEncoder: Encoder[PeoplePojo] = Encoders.kryo[PeoplePojo]
    val expList = List(PeoplePojo("John", 25), PeoplePojo("Petr", 30))
    val ds = Factory.ss.createDataset(expList)
    val actList = ds.collect()
    ds.columns should contain("value")
    actList should contain allElementsOf expList
  }

  it should "init DataSet with POJO (product endocer)" in {
    implicit val mapEncoder: Encoder[PeoplePojo] = Encoders.product[PeoplePojo]
    val expList = List(PeoplePojo("John", 25), PeoplePojo("Petr", 30))
    val ds = Factory.ss.createDataset(expList)
    val actList = ds.collect()
    ds.columns should contain allOf("name", "age")
    actList should contain allElementsOf expList
  }
}

private case class PeoplePojo(name: String, age: Int) {}
