package collection.seq

import org.scalatest.{FlatSpec, Matchers}

class ReplaceElementTest extends FlatSpec with Matchers {

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
