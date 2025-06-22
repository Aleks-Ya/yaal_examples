package scala.clazz.traits

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Initialization of fields declared in a trait.
 */
class FieldInTraitTest extends AnyFlatSpec with Matchers {

  it should "use val field from trait" in {
    val a = new UseTraitFieldsA()
    val b = new UseTraitFieldsB()
    a.getCity shouldEqual "London UseTraitFieldsA"
    b.getCity shouldEqual "London UseTraitFieldsB"
  }

  it should "use lazy val field from trait" in {
    val a = new UseTraitFieldsA()
    val b = new UseTraitFieldsB()
    a.getStreet shouldEqual "Oxford UseTraitFieldsA"
    b.getStreet shouldEqual "Oxford UseTraitFieldsB"
  }

  it should "use implicit lazy val field from trait" in {
    val a = new UseTraitFieldsA()
    val b = new UseTraitFieldsB()
    a.getDistrict shouldEqual "Westminster UseTraitFieldsA"
    b.getDistrict shouldEqual "Westminster UseTraitFieldsB"
  }

}
