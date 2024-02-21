import sbt.*

object Dependencies {
  val scalaTestDep = "org.scalatest" %% "scalatest" % "3.2.17" % Test
  val scalaMockDep = "org.scalamock" %% "scalamock" % "5.2.0" % Test
  val h2Dep = "com.h2database" % "h2" % "2.2.224"
  val logbackClassicDep = "ch.qos.logback" % "logback-classic" % "1.4.11"
  val jettyServletDep = "org.eclipse.jetty" % "jetty-servlet" % "11.0.14" % Test

  private val slf4jVersion = "2.0.9"
  val slf4jNopDep = "org.slf4j" % "slf4j-nop" % slf4jVersion
  val slf4jSimpleDep = "org.slf4j" % "slf4j-simple" % slf4jVersion

  private val slickVersion = "3.4.1"
  private val playVersion = "2.8.21"
  val slickDep = "com.typesafe.slick" %% "slick" % slickVersion
  val slickHikaricpDep = "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
  val slickTestkitDep = "com.typesafe.slick" %% "slick-testkit" % slickVersion % Test
  val playLiquibaseDep = "com.ticketfly" %% "play-liquibase" % "2.2"
  val playSlickDep = "com.typesafe.play" %% "play-slick" % "5.1.0"
  val playJdbcDep = "com.typesafe.play" %% "play-jdbc" % playVersion
  val playTestDep = "com.typesafe.play" %% "play-test" % playVersion
  val scalaTestPlusPlayDep = "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

  val jsonUnitDep = "net.javacrumbs.json-unit" % "json-unit" % "2.38.0" % Test
}
