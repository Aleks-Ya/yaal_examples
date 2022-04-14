package dataset

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConcurrentModificationExceptionTest extends AnyFlatSpec with Matchers {

  it should "work without exception (because 'ss' inside the method" in {
    val ss: SparkSession = Factory.ss
    class Person(val gender: String, val name: String) extends Serializable {}
    implicit val mapEncoder: Encoder[Person] = Encoders.kryo[Person]
    val ds = ss.createDataset(Seq(new Person("M", "John"), new Person("F", "Mary")))
    ds.show
  }

  val ss: SparkSession = Factory.ss

  it should "work with exception (because 'ss' is out of the method)" in {
    class Person(val gender: String, val name: String) extends Serializable {}
    implicit val mapEncoder: Encoder[Person] = Encoders.kryo[Person]
    assertThrows[RuntimeException] {
      ss.createDataset(Seq(new Person("M", "John"), new Person("F", "Mary")))
    }
  }

}
