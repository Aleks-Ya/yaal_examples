// Run Scala application with "sbt run" command and reload modified classes

import Dependencies.scalaTestDep

lazy val ScalaRun = (project in file(".")).settings(
  libraryDependencies ++= Seq(scalaTestDep),
  assembly / mainClass := Some("run.ReloadMain"),
  Global / cancelable := true,
  run / fork := true
)

