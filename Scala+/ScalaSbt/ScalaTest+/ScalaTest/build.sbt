import Dependencies.scalaTestDep

lazy val ScalaTest = (project in file(".")).settings(libraryDependencies ++= Seq(scalaTestDep))
