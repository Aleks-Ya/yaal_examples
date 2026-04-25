package spark3.encoders

import io.github.pashashiz.spark_encoders.TypedEncoder
import io.github.pashashiz.spark_encoders.TypedEncoder._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class EncodersTest extends AnyFlatSpec with Matchers {

  it should "encode a case class" in {
    val users = Seq(User("Pavlo", 35), User("Randy", 45))
    val df = Factory.ss.createDataset(users)
    df shouldHaveDDL "name STRING NOT NULL,age INT NOT NULL"
    df shouldContain(
      """{"name":"Pavlo","age":35}""",
      """{"name":"Randy","age":45}"""
    )
  }

  it should "encode an ADT (sealed trait)" in {
    val items = Seq(Item.Defect("d1", 100), Item.Feature("f1", 1), Item.Story("s1", 3))
    val df = Factory.ss.createDataset[Item](items)
    df shouldHaveDDL "_type STRING NOT NULL,name STRING NOT NULL,points INT,priority INT,size INT"
    df shouldContain(
      """{"_type":"Defect","name":"d1","points":null,"priority":100,"size":null}""",
      """{"_type":"Feature","name":"f1","points":null,"priority":null,"size":1}""",
      """{"_type":"Story","name":"s1","points":3,"priority":null,"size":null}"""
    )
  }

  it should "encode an enum (sealed trait)" in {
    val colors = Seq(Color.Red, Color.Green, Color.Blue)
    val df = Factory.ss.createDataset[Color](colors)
    df shouldHaveDDL "value STRING NOT NULL"
    df shouldContain(
      """{"value":"Red"}""",
      """{"value":"Green"}""",
      """{"value":"Blue"}"""
    )
  }

  it should "use a custom encoder" in {
    implicit val altTE: TypedEncoder[Altitude] = xmap[Altitude, Double](_.value)(Altitude.apply)
    val altitudes = Seq(Altitude(100), Altitude(132))
    val df = Factory.ss.createDataset[Altitude](altitudes)
    df shouldHaveDDL "value DOUBLE NOT NULL"
    df shouldContain(
      """{"value":100.0}""",
      """{"value":132.0}"""
    )
  }

}
