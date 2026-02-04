package core

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NotSerializableTest extends AnyFlatSpec with Matchers {

  it should "process not serializable class with a serializable wrapper" in {
    class Wrapper(data: Data) extends Serializable
    class Data(text: String)
    val data = Seq(new Wrapper(new Data("a")), new Wrapper(new Data("b")))
    val list = Factory.sc.parallelize(data).collect()
    println(list.length)
  }
}
