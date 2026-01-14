package scala.clazz.`implicit`.parameter

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ImplicitParameterTest extends AnyFlatSpec with Matchers {

  it should "use implicit parameters" in {
    case class WeightUnit(title: String)
    implicit val kgWeightUnit: WeightUnit = WeightUnit("kg")
    object WeightFormatter {
      def formatWeight(weight: Double)(implicit weightUnit: WeightUnit): String = weight + " " + weightUnit.title
    }
    val weight = 100
    val weightStr = WeightFormatter.formatWeight(weight)
    weightStr shouldEqual "100.0 kg"
  }

  it should "use implicit parameters with Generics" in {
    implicit val titles: Seq[String] = Seq("kg", "m")
    implicit val priorities: Seq[Int] = Seq(1, 2)

    object Formatter {
      def printTitles()(implicit titleSeq: Seq[String]): String = titleSeq.mkString(", ")

      def printPriorities()(implicit prioritiesSeq: Seq[Int]): String = prioritiesSeq.mkString(", ")
    }

    Formatter.printTitles() shouldEqual "kg, m"
    Formatter.printPriorities() shouldEqual "1, 2"
  }

}
