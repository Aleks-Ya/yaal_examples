import sbt._

object Dependencies {
  val scalaVer = "2.12.17"
  val allDeps: Seq[ModuleID] = Seq(
    "org.scala-lang" % "scala-library" % scalaVer,
    "org.apache.spark" %% "spark-sql" % "3.3.1",
    "org.apache.livy" % "livy-client-http" % "0.7.1-incubating"
  )
}
