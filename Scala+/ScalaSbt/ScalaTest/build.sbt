import Dependencies._

lazy val ScalaTest = (project in file(".")).settings(libraryDependencies ++= Seq(scalaTestDep))
