package scala.clazz.`implicit`.parameter

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TypeImplicitParameterTest extends AnyFlatSpec with Matchers {

  it should "use a type as an implicit parameter" in {
    //    implicit val name: Name = "John"
    implicit val surname: Surname = "Smith"
    object GreatFormatter {
      def format()(implicit person: Surname): String = s"Hello, Mr. $person"
    }
    val weightStr = GreatFormatter.format()
    weightStr shouldEqual "Hello, Mr. Smith"
  }

  type Name = String
  type Surname = String

}
