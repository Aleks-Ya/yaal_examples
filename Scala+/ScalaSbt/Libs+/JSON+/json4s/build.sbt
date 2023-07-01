import Dependencies.{json4sNativeDep, scalaTestDep}

lazy val json4s = (project in file(".")).settings(libraryDependencies ++= Seq(json4sNativeDep, scalaTestDep))
