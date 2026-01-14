package config

import com.typesafe.config.{Config, ConfigFactory}

object ConfigReloadable {
  val config: Config = ConfigFactory.load(getClass.getClassLoader, "config/init.conf")

  val magicNumber: Int = config.getInt("magic.number")
}
