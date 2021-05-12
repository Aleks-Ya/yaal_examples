import sbt._

object Dependencies {
  val scalaTestDep = "org.scalatest" %% "scalatest" % "3.2.3" % Test
  val scalaMockDep = "org.scalamock" %% "scalamock" % "5.1.0" % Test
  val h2Dep = "com.h2database" % "h2" % "1.4.200"
  val slf4jNopDep = "org.slf4j" % "slf4j-nop" % "1.7.30"
  val logbackClassicDep = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val scoptDep = "com.github.scopt" %% "scopt" % "4.0.0"
  val json4sNativeDep = "org.json4s" %% "json4s-native" % "3.6.10"
  val jettyServletDep = "org.eclipse.jetty" % "jetty-servlet" % "9.4.36.v20210114" % Test

  private val akkaVersion = "2.6.12"
  val akkaActorTypedDep = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
  val akkaActorTestKitTypedDep = "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test

  private val slickVersion = "3.3.3"
  private val playVersion = "2.8.7"
  val slickDep = "com.typesafe.slick" %% "slick" % slickVersion
  val slickHikaricpDep = "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
  val slickTestkitDep = "com.typesafe.slick" %% "slick-testkit" % slickVersion % Test
  val playLiquibaseDep = "com.ticketfly" %% "play-liquibase" % "2.2"
  val playSlickDep = "com.typesafe.play" %% "play-slick" % "5.0.0"
  val playJdbcDep = "com.typesafe.play" %% "play-jdbc" % playVersion
  val playTestDep = "com.typesafe.play" %% "play-test" % playVersion
  val scalaTestPlusPlayDep = "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

  val typesafeConfigDep = "com.typesafe" % "config" % "1.4.1"

  val jsonUnitDep = "net.javacrumbs.json-unit" % "json-unit" % "2.24.0" % Test

  private val spark3Version = "3.1.1"
  val spark3CoreDep = "org.apache.spark" %% "spark-core" % spark3Version
  val spark3StreamingDep = "org.apache.spark" %% "spark-streaming" % spark3Version
}
