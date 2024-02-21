package example

import scala.io.Source

object Hello extends Greeting with App {
  println(greeting)
}

trait Greeting {
  lazy val greeting: String = {
    val source = Source.fromResource("example/main.txt")
    val content = source.getLines().mkString
    source.close()
    content
  }
}
