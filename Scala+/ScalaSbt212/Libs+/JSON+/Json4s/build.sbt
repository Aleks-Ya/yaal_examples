import Dependencies.{json4sNativeDep, scalaTestDep}

lazy val Json4s = (project in file(".")).settings(libraryDependencies ++= Seq(json4sNativeDep, scalaTestDep))
