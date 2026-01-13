import Dependencies.{databricksSdkJavaDep, scalaTestDep}

lazy val DatabricksJavaSdk = (project in file("."))
  .settings(libraryDependencies ++= Seq(scalaTestDep, databricksSdkJavaDep))