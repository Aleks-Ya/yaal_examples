package dataframe.create

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.beans.BeanProperty

class SeqToDfTest extends AnyFlatSpec with Matchers {

  it should "infer schema from rows" in {
    import Factory.ss.implicits._
    val data = Seq(("John", 25), ("Peter", 35))
    val df = data.toDF("name", "age")
    df.schema.simpleString shouldEqual "struct<name:string,age:int>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25}""",
      """{"name":"Peter","age":35}"""
    )
  }

  it should "create DataFrame of numbers" in {
    import Factory.ss.implicits._
    val df = (1 to 3).toDF("id")
    df.toJSON.collect() should contain inOrderOnly(
      """{"id":1}""",
      """{"id":2}""",
      """{"id":3}"""
    )
  }

  it should "convert a Seq of POJOs to DataFrame " in {
    import Factory.ss.implicits._
    val data = Seq(People("John", 25), People("Peter", 35))
    val df = data.toDF("name", "age")
    df.schema.simpleString shouldEqual "struct<name:string,age:int>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25}""",
      """{"name":"Peter","age":35}"""
    )
  }

  it should "convert a Seq of tuples to DataFrame " in {
    import Factory.ss.implicits._
    val data = Seq(("London", People("John", 25)), ("Berlin", People("Peter", 35)))
    val df = data.toDF("city", "person")
    df.schema.simpleString shouldEqual "struct<city:string,person:struct<name:string,age:int>>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"city":"London","person":{"name":"John","age":25}}""",
      """{"city":"Berlin","person":{"name":"Peter","age":35}}"""
    )
  }

  it should "convert a Seq of tuples with a Seq to DataFrame " in {
    import Factory.ss.implicits._
    val data = Seq(
      ("London", Seq(People("John", 25), People("Peter", 35))),
      ("Berlin", Seq(People("Mike", 30), People("Mary", 20)))
    )
    val df = data.toDF("city", "people")
    df.schema.simpleString shouldEqual "struct<city:string,people:array<struct<name:string,age:int>>>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"city":"London","people":[{"name":"John","age":25},{"name":"Peter","age":35}]}""",
      """{"city":"Berlin","people":[{"name":"Mike","age":30},{"name":"Mary","age":20}]}"""
    )
  }

}

case class People(@BeanProperty var name: String, @BeanProperty var age: Int)
