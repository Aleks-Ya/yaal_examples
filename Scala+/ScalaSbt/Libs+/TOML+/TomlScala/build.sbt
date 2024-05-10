import Dependencies.{tomlScalaDep, scalaTestDep}

lazy val TomlScala = (project in file(".")).settings(libraryDependencies ++= Seq(tomlScalaDep, scalaTestDep))
