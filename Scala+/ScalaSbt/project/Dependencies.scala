import sbt._

object Dependencies {
  val scalaTestDep = "org.scalatest" %% "scalatest" % "3.2.2" % Test
  val scalaMockDep = "org.scalamock" %% "scalamock" % "5.0.0" % Test
  val h2Dep = "com.h2database" % "h2" % "1.4.200"
  val slf4jNopDep = "org.slf4j" % "slf4j-nop" % "1.6.4"
  val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"
}
