package http4s

import org.http4s.Charset
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CharsetTest extends AnyFlatSpec with Matchers {
  it should "get charset by name" in {
    val result = Charset.fromString("UTF-8")
    result.getOrElse() shouldEqual Charset.`UTF-8`
  }
}
