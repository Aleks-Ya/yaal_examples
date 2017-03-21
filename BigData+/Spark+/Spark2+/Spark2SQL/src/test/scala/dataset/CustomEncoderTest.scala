package dataset

import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class CustomEncoderTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  var ss: SparkSession = _

  override def beforeAll() {
    ss = SparkSession.builder()
      .appName(classOf[CustomEncoderTest].getSimpleName)
      .master("local")
      .getOrCreate()
  }

  it should "init dataset" in {
    val sqlContext = ss.sqlContext
    import sqlContext.implicits._
    val ds = ss.createDataset(Seq("John", "Mary"))
    ds.show
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[People]
    val peopleDs = ds.map(name => new People(name))
    peopleDs.show
    peopleDs.foreach(println(_))
  }

  override def afterAll() {
    ss.stop()
  }
}

class People(val name: String) extends Serializable {}
