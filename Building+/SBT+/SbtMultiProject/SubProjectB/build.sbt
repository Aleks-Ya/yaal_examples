import Dependencies._

lazy val subProjectB = (project in file("."))
  .settings(
    name := "SubProjectB",
    libraryDependencies += scalaTest
  )
