package scalamock

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SubClassMockTest extends AnyFlatSpec with Matchers with MockFactory {

  it should "mock a sub-class" in {
    abstract class SuperClass(name: String) {
      def takeName(): String = name
    }
    class SubClass(name: String, age: Int) extends SuperClass(name) {
      def takeAge(): Int = age
    }
    val subClassMock = mock[SubClass]
    val expName = "Russia"
    val expAge = 30
    (subClassMock.takeName _).expects().returning(expName)
    (subClassMock.takeAge _).expects().returning(expAge)
    subClassMock.takeName() shouldEqual expName
    subClassMock.takeAge() shouldEqual expAge
  }

  it should "mock a sub-class with implicit" in {
    implicit val name: String = "abc"
    abstract class SuperClass(name: String) {
      def takeName(): String = name
    }
    class SubClass(age: Int)(implicit name: String) extends SuperClass(name) {
      def takeAge(): Int = age
    }
    val subClassMock = mock[SubClass]
    val expName = "Russia"
    val expAge = 30
    (subClassMock.takeName _).expects().returning(expName)
    (subClassMock.takeAge _).expects().returning(expAge)
    subClassMock.takeName() shouldEqual expName
    subClassMock.takeAge() shouldEqual expAge
  }

  it should "not invoke super-class constructor" in {
    val expMessage = "Don't invoke super-class constructor!"
    abstract class SuperClass {
      throw new IllegalStateException(expMessage)
    }
    class SubClass extends SuperClass {
    }
    val e = intercept[IllegalStateException] {
      mock[SubClass]
    }
    e.getMessage shouldBe expMessage
  }

}