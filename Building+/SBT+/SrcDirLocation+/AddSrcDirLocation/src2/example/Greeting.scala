package example

import scala.io.Source

trait Greeting {
  lazy val greeting: String = {
    val source1 = Source.fromResource("example/main1.txt")
    val content1 = source1.getLines().mkString
    source1.close()

    val source2 = Source.fromResource("example/main2.txt")
    val content2 = source2.getLines().mkString
    source2.close()

    s"$content1 $content2"
  }
}