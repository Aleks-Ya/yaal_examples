import Dependencies.{scalaTestDep, scalaMockDep}

lazy val ScalaMock = (project in file(".")).settings(libraryDependencies ++= Seq(scalaTestDep, scalaMockDep))
