package scala.clazz.`implicit`.conversion

import org.scalatest.{FlatSpec, Matchers}

/**
 * Source: https://www.baeldung.com/scala/implicit-conversions
 **/
class LengthConversion extends FlatSpec with Matchers {

  it should "use implicit converter method" in {
    implicit def meters2centimeters(meters: Meters): Centimeters = Centimeters(meters.value * 100)

    val centimeters: Centimeters = Meters(2.5)
    centimeters shouldBe Centimeters(250)
  }

  it should "use implicit converter function" in {
    implicit val kilometers2meters: Kilometers => Meters = kilometers => Meters(kilometers.value * 1000)

    val meters: Meters = Kilometers(2.5)
    meters shouldBe Meters(2500)
  }

  it should "add additional methods to class" in {
    class LengthSyntax(value: Double) {
      def centimeters: Centimeters = Centimeters(value)

      def meters: Meters = Meters(value)

      def kilometers: Kilometers = Kilometers(value)
    }

    implicit def double2richSyntax(value: Double): LengthSyntax =
      new LengthSyntax(value)

    val length: Double = 2.5

    length.centimeters shouldBe Centimeters(length)
    length.meters shouldBe Meters(length)
    length.kilometers shouldBe Kilometers(length)
  }
}


case class Centimeters(value: Double) extends AnyVal

case class Meters(value: Double) extends AnyVal

case class Kilometers(value: Double) extends AnyVal
