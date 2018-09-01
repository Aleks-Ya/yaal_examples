package dataset

import factory.Factory
import org.scalatest.{FlatSpec, Matchers}

class InCodeDfTest extends FlatSpec with Matchers {

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
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[PeopleInCode]
    val list = List(PeopleInCode("John", 25), PeopleInCode("Petr", 30))
    val ds = Factory.ss.createDataset(list)

  }

}

case class PeopleInCode(name: String, age: Int) {}