package scala.clazz.`implicit`.parameter

import org.scalatest.{FlatSpec, Matchers}

class FormatWeightExample extends FlatSpec with Matchers {

  it should "use implicit parameters" in {
    case class WeightUnit(title: String)
    implicit val kgWeightUnit: WeightUnit = WeightUnit("kg")
    object WeightFormatter {
      def formatWeight(weight: Double)(implicit weightUnit: WeightUnit): String = {
        weight + " " + weightUnit.title
      }
    }
    val weight = 100
    val weightStr = WeightFormatter.formatWeight(weight)
    weightStr shouldEqual "100.0 kg"
  }

}
