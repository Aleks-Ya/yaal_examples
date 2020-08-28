package scalamock.style.expectationfirst

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MockFunction extends AnyFlatSpec with Matchers with MockFactory {

  it should "mock a function" in {
    val functionMock = mockFunction[Int, String]
    val expText = "Forty two"
    val num = 42
    functionMock expects num returning expText
    val actText = functionMock(num)
    actText shouldEqual expText
  }

}