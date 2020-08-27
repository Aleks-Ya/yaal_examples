package scala.clazz.sealed_class

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class SealedClass extends AnyFlatSpec with Matchers

/**
  * A sealed class or trait can be extended only in the same source file.
  */
sealed class Animal

final class Cat extends Animal

final class Dog extends Animal

