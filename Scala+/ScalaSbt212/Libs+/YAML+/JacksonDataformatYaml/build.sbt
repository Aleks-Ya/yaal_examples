import Dependencies.{jacksonDataformatYamlDep, jacksonModuleScalaDep, scalaTestDep}

lazy val JacksonDataformatYaml = (project in file("."))
  .settings(libraryDependencies ++= Seq(jacksonModuleScalaDep, jacksonDataformatYamlDep, scalaTestDep))
