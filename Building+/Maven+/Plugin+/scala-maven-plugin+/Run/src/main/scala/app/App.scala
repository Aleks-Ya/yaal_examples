package app

object App {

  def main(args: Array[String]): Unit = {
    println("Hello World!")
    println("concat arguments = " + foo(args))
  }

  private def foo(x: Array[String]): String = x.foldLeft("")((a, b) => a + b)
}
