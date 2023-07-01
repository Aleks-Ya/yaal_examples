import Dependencies.{scoptDep, scalaTestDep}

lazy val ScalaScopt = (project in file(".")).settings(libraryDependencies ++= Seq(scoptDep, scalaTestDep))
