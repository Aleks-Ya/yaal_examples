// Run Scala application with "sbt run" command and reload modified classes

import Dependencies._

lazy val ScalaRun = (project in file(".")).settings(
  libraryDependencies ++= Seq(scalaTestDep),
  mainClass in assembly := Some("run.ReloadMain"),
  cancelable in Global := true,
  fork in run := true
)

