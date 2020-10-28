import Dependencies._

lazy val ScalaMock = (project in file(".")).settings(libraryDependencies ++= Seq(scalaTestDep, scalaMockDep))
