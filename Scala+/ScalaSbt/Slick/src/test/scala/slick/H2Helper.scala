package slick

import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.api._

import scala.util.Random

trait H2Helper {
  protected def h2Database: H2Profile.backend.DatabaseDef =
    Database.forURL(
      url = "jdbc:h2:mem:test" + Random.nextInt(Int.MaxValue),
      driver = "org.h2.Driver",
      keepAliveConnection = true)
}
