package dataset

import factory.Factory
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class CustomEncoderTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  it should "init dataset" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset(Seq("John", "Mary"))
    ds.show
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[People]
    val peopleDs = ds.map(name => new People(name))
    peopleDs.show
    peopleDs.foreach(println(_))
  }

}

class People(val name: String) extends Serializable {}
