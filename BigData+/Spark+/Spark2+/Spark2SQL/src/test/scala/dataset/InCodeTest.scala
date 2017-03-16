package dataset

import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class InCodeTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  var ss: SparkSession = _

  override def beforeAll() {
    ss = SparkSession.builder()
      .appName(classOf[InCodeTest].getSimpleName)
      .master("local")
      .getOrCreate()
  }

  it should "init dataset" in {
    val sqlContext = ss.sqlContext
    import sqlContext.implicits._
    val ds = ss.createDataset(Seq("a", "b"))
    ds.show
  }

  override def afterAll() {
    ss.stop()
  }
}