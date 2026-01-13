import Dependencies.scalaTestDep

lazy val ScalaCore = (project in file(".")).settings(libraryDependencies ++= Seq(scalaTestDep))
