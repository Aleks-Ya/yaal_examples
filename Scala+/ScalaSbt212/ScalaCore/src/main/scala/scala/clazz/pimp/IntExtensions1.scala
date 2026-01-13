package scala.clazz.pimp

object IntExtensions1 {
  implicit class IntExtended1(val instance: Int) extends AnyVal {
    def even(): Boolean = instance % 2 == 0
  }
}