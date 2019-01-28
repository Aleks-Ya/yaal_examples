package scala.clazz.sealed_class

import org.scalatest.{FlatSpec, Matchers}


class SealedClass extends FlatSpec with Matchers

/**
  * A sealed class or trait can be extended only in the same source file.
  */
sealed class Animal

final class Cat extends Animal

final class Dog extends Animal

