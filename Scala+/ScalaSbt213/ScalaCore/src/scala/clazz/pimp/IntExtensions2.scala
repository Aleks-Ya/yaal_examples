package scala.clazz.pimp

object IntExtensions2 {
  implicit class IntExtended2(val instance: Int) extends AnyVal {
    def odd(): Boolean = instance % 2 != 0
  }
}