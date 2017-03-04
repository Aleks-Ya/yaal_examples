package matcher

import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import scala.collection.immutable._

class SequencesTest extends FlatSpec {

  "sequences" should "work" in {
    List(1, 2, 2, 3, 3, 3) should contain inOrderOnly (1, 2, 3)
    List(1, 2, 2, 3, 3, 3) should contain (1)
    //List(1, 2, 2, 3, 3, 3) should contain (1)// don't work
  }

}