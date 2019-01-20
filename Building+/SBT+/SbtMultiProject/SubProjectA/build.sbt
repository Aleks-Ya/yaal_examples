import Dependencies._

lazy val subProjectA = (project in file(".")).
  settings(
    name := "SubProjectA",
    libraryDependencies += scalaTest
  )
