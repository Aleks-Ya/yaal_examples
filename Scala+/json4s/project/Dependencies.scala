import sbt._

object Dependencies {
  val allDeps: Seq[ModuleID] = Seq(
    "org.json4s" % "json4s-native_2.12" % "3.6.3",
    "org.scalatest" % "scalatest_2.12" % "3.0.5" % Test
  )
}
