package dataset

import factory.Factory
import org.apache.spark.sql.Encoder
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class CustomEncoderTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  it should "init dataset" in {
    val ss = Factory.ss
    import ss.sqlContext.implicits._
    val ds = ss.createDataset(Seq("John", "Mary"))
    ds.show

    class People(val name: String) extends Serializable {}
    implicit val mapEncoder: Encoder[People] = org.apache.spark.sql.Encoders.kryo[People]
    val peopleDs = ds.map(name => new People(name))
    peopleDs.show
    peopleDs.foreach(println(_))
  }

}

