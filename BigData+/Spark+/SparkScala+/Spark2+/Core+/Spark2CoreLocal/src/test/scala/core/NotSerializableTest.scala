package core

import org.scalatest.{FlatSpec, Matchers}

class NotSerializableTest extends FlatSpec with Matchers {

  it should "process not serializable class with a serializable wrapper" in {
    class Wrapper(data: Data) extends Serializable
    class Data(text: String)
    val data = Seq(new Wrapper(new Data("a")), new Wrapper(new Data("b")))
    val list = Factory.sc.parallelize(data).collect()
    println(list.length)
  }
}
