package scala.collection.map

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.IterableView

class MapViewTest extends AnyFlatSpec with Matchers {

  it should "use a map view" in {
    val map1: Map[String, Int] = Map("x" -> 24, "y" -> 25)
    val view1: AnyRef with IterableView[(String, Int), Map[String, Int]] = map1.view
    val view2: IterableView[(String, Int), Iterable[_]] = view1.map(kv => (kv._1, kv._2 * 2))
    val map2: Map[String, Int] = view2.toMap
    map2 shouldEqual Map("x" -> 48, "y" -> 50)
  }

}
