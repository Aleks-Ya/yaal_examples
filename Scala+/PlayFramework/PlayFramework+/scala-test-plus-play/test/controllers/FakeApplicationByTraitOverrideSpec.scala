package controllers

import org.scalatest.TestData
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

/**
 * Creating FakeApplication by GuiceOneAppPerTest trait, but override it with custom in this test.
 */
class FakeApplicationByTraitOverrideSpec extends PlaySpec with GuiceOneAppPerTest {

  // Override newAppForTest if you need an Application with other than non-default parameters.
  implicit override def newAppForTest(testData: TestData): Application =
    new GuiceApplicationBuilder().configure(Map("title" -> "Mr")).build()

  "Test" should {
    "override FakeApplication" in {
      app.configuration.getOptional[String]("title") mustBe Some("Mr")
    }
  }
}
