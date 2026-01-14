package scala.clazz.reflection

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.reflect.runtime.universe._

//NOT WORKING
class FindTraitImplementationsTest extends AnyFlatSpec with Matchers {

  it should "get implementations" in {
    val subClasses = subclassesOf[Vehicle]
    println(subClasses)
  }

  private def subclassesOf[T: TypeTag]: Set[Class[_]] = {
    val symbol = typeOf[T].typeSymbol.asClass
    val mirror = runtimeMirror(getClass.getClassLoader)
    symbol.knownDirectSubclasses
      .map(_.asClass)
      .map(cls => mirror.runtimeClass(cls))
  }

}