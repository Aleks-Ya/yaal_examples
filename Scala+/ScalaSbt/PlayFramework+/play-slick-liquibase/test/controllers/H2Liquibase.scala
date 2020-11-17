package controllers

import java.nio.file.Files.createTempFile

import org.scalatest.TestData
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Application
import play.api.db.Database
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.Injecting
import slick.jdbc.H2Profile.api._

abstract class H2Liquibase extends PlaySpec with GuiceOneAppPerTest with Injecting {
  implicit override def newAppForTest(testData: TestData): Application =
    new GuiceApplicationBuilder()
      .configure(Map(
        "db.default.driver" -> "org.h2.Driver",
        "db.default.url" -> s"jdbc:h2:${createTempFile(getClass.getSimpleName, ".h2")};AUTO_SERVER=TRUE",
        "db.default.username" -> "sa",
        "db.default.password" -> "",
        "liquibase.changelog" -> getClass.getClassLoader.getResource("liquibase/changelog.xml").getFile,
        "liquibase.enable" -> "true"
      )).build()

  lazy val db = Database.forDataSource(app.injector.instanceOf[Database].dataSource, None, keepAliveConnection = true)
}
