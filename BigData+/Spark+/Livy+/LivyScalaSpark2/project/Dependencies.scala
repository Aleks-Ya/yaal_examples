import sbt._

object Dependencies {
  val scalaVer = "2.11.12"
  val allDeps: Seq[ModuleID] = Seq(
    "org.scala-lang" % "scala-library" % scalaVer,
    "org.apache.spark" %% "spark-sql" % "2.4.8",
    "org.apache.livy" % "livy-client-http" % "0.7.1-incubating"
  )
}
