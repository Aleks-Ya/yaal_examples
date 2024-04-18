import Dependencies.scalaTestDep

lazy val UtilSrc = (project in file(".")).settings(libraryDependencies ++= Seq(scalaTestDep))
