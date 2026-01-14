package scala.clazz.`implicit`.parameter

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OptionalImplicitParameterTest extends AnyFlatSpec with Matchers {

  it should "use optional implicit parameter (Some)" in {
    implicit val kgWeightUnit: Option[WeightUnit] = Some(WeightUnit("kg"))
    val weight = 100
    val weightStr = WeightFormatter.formatWeight(weight)
    weightStr shouldEqual "100.0 kg"
  }

  it should "use optional implicit parameter (None exists)" in {
    implicit val kgWeightUnit: Option[WeightUnit] = None
    val weight = 100
    val weightStr = WeightFormatter.formatWeight(weight)
    weightStr shouldEqual "100.0 g"
  }

  it should "use optional implicit parameter (None missing)" in {
    val weight = 100
    val weightStr = WeightFormatter.formatWeight(weight)
    weightStr shouldEqual "100.0 g"
  }

  case class WeightUnit(title: String)

  object WeightFormatter {
    def formatWeight(weight: Double)(implicit weightUnit: Option[WeightUnit] = None): String =
      weight + " " + weightUnit.map(_.title).getOrElse("g")
  }

}
