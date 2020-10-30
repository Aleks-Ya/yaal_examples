import Dependencies._

lazy val ScalaScopt = (project in file(".")).settings(libraryDependencies ++= Seq(scoptDep, scalaTestDep))
