package scala.collection.buffer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

class BufferTest extends AnyFlatSpec with Matchers {

  it should "append" in {
    val b = mutable.Buffer[Int]()
    b += 1
    b ++= Seq(2, 3)
    b should contain allOf(1, 2, 3)
  }

  it should "create an empty Buffer" in {
    val b = mutable.Buffer.empty[Int]
    b shouldBe empty
  }

  it should "convert Buffer to mutable Seq" in {
    val b: mutable.Seq[Int] = mutable.Buffer.empty[Int]
    b shouldBe a[mutable.Buffer[_]]
    b shouldBe a[mutable.Seq[_]]
  }

  it should "convert Buffer to immutable Seq" in {
    val mutableSeq: mutable.Seq[Int] = mutable.Buffer.empty[Int]
    val immutableSeq: Seq[Int] = mutableSeq.toSeq
    immutableSeq shouldBe a[Seq[_]]
  }

}
