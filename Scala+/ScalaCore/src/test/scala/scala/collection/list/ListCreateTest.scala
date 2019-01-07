package scala.collection.list

import org.scalatest.{FlatSpec, Matchers}

class ListCreateTest extends FlatSpec with Matchers {

  it should "create a list from elements and another list" in {
    val another = List(3, 4)
    val list = 1 :: 2 :: another
    list shouldBe a[List[_]]
  }

  it should "create a list with Nil" in {
    val list = 1 :: 2 :: Nil
    list shouldBe a[List[_]]
  }

  it should "declare explicit type of a list" in {
    val list: List[Double] = List(1, 2, 3)
    list shouldBe a[List[_]]
  }

  it should "create an empty list of specified type" in {
    val list: List[Double] = List()
    list shouldBe a[List[Double]]
  }

}
