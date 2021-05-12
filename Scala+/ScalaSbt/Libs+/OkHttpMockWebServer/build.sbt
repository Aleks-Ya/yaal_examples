import Dependencies.{scalaTestDep, mockWebServerDep}

lazy val OkHttpMockWebServer = (project in file("."))
  .settings(libraryDependencies ++= Seq(scalaTestDep, mockWebServerDep))