package config

/**
 * Run: sbt> ; project TypesafeConfig; runMain config.ConfigReloadableMain
 */
object ConfigReloadableMain {
  def main(args: Array[String]): Unit = {
    val magicNumber = ConfigReloadable.magicNumber
    while (true) {
      Thread.sleep(2000)
      val formatted = MagicNumberFormatter.format(magicNumber)
      print(formatted)
    }
  }
}
