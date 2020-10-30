import Dependencies._

lazy val ScalaCore = (project in file(".")).settings(libraryDependencies ++= Seq(scalaTestDep))
