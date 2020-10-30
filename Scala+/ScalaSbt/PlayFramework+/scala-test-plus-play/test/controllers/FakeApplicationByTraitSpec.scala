package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._

/**
 * Creating FakeApplication by GuiceOneAppPerTest trait.
 */
class FakeApplicationByTraitSpec extends PlaySpec with GuiceOneAppPerTest {

  "Test" should {
    "run FakeApplication" in {
      app.configuration.getOptional[String]("play.assets.urlPrefix") mustBe Some("/assets")
    }
  }
}
