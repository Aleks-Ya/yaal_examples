package collection.create

import org.scalatest.{FlatSpec, Matchers}

class ListCreationTest extends FlatSpec with Matchers {

  it should "create an array" in {
    val list = 1 :: 2 :: Nil
    list shouldBe a[List[_]]

    val other = List(3, 4)
    val list2 = 1 :: 2 :: other
    list2 shouldBe a[List[_]]
  }

}
