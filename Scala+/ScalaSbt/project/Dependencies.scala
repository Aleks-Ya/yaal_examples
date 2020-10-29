import sbt._

object Dependencies {
  val scalaTestDep = "org.scalatest" %% "scalatest" % "3.2.2" % Test
  val scalaMockDep = "org.scalamock" %% "scalamock" % "5.0.0" % Test
  val h2Dep = "com.h2database" % "h2" % "1.4.200"
  val slf4jNopDep = "org.slf4j" % "slf4j-nop" % "1.6.4"
  val logbackClassicDep = "ch.qos.logback" % "logback-classic" % "1.2.3"

  private val slickVersion = "3.3.3"
  val slickDep = "com.typesafe.slick" %% "slick" % slickVersion
  val slickHikaricpDep = "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
  val slickTestkitDep = "com.typesafe.slick" %% "slick-testkit" % slickVersion % Test
  val playLiquibaseDep = "com.ticketfly" %% "play-liquibase" % "2.2"
  val playSlickDep = "com.typesafe.play" %% "play-slick" % "5.0.0"
  val playJdbcDep = "com.typesafe.play" %% "play-jdbc" % "2.8.3"
  val scalaTestPlusPlayDep = "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
}
