package dataset

import factory.Factory
import org.apache.spark.sql.Encoder
import org.scalatest.{FlatSpec, Matchers}

class InCodeTest extends FlatSpec with Matchers {

  it should "init DataSet" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset(Seq("a", "b"))
    ds.show
  }

  it should "init DataSet with Seq" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Seq("c", "d").toDS()
    ds.show
  }

  it should "init DataSet with POJO" in {
    implicit val mapEncoder: Encoder[PeoplePojo] = org.apache.spark.sql.Encoders.kryo[PeoplePojo]
    val list = List(PeoplePojo("John", 25), PeoplePojo("Petr", 30))
    val people = Factory.ss.createDataset(list).collect()
    people should contain allElementsOf list
  }

}

case class PeoplePojo(name: String, age: Int) {}