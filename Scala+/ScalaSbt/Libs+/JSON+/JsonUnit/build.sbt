import Dependencies.{jsonUnitDep, scalaTestDep}

lazy val JsonUnit = (project in file(".")).settings(libraryDependencies ++= Seq(jsonUnitDep, scalaTestDep))
