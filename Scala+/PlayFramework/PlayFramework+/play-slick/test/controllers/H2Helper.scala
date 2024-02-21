package controllers

import com.typesafe.config.ConfigFactory
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.{BasicProfile, DatabaseConfig}
import slick.jdbc.{H2Profile, JdbcProfile}

import scala.collection.JavaConverters._

object H2Helper {
  private lazy val configMap = Map(
    "profile" -> H2Profile.getClass.getName,
    "db.url" -> "jdbc:h2:mem:test1",
    "db.driver" -> "org.h2.Driver",
  ).asJava
  private lazy val config = ConfigFactory.parseMap(configMap)
  lazy val dbConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig(path = "", config = config)

  lazy val databaseConfigProvider: DatabaseConfigProvider = new MockDatabaseConfigProvider(dbConfig)

  class MockDatabaseConfigProvider(private val config: DatabaseConfig[JdbcProfile]) extends DatabaseConfigProvider {
    override def get[P <: BasicProfile] = config.asInstanceOf[DatabaseConfig[P]]
  }

}
