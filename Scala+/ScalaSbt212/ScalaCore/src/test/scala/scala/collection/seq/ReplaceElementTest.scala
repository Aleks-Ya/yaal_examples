package scala.collection.seq

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReplaceElementTest extends AnyFlatSpec with Matchers {

  it should "replace several elements in sequence" in {
    val s = Seq(1, 2, 3, 4)
    val patched = s.patch(1, Seq(7, 8), 2)
    patched should contain inOrderOnly(1, 7, 8, 4)
  }

  it should "replace single elements in sequence" in {
    val s = Seq(1, 2, 3, 4)
    val updated = s.updated(2, 8)
    updated should contain inOrderOnly(1, 2, 8, 4)
  }

}
