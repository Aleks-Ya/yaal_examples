package scala.clazz.traits

class Calculator extends MathHelper with StringHelper {
  def calculate(a: Int, b: Int, c: Int): String = format(plus(multiply(a, b), c))
}
