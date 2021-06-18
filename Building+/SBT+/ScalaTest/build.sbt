import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ru.yaal",
      scalaVersion := "2.13.6",
      version      := "1"
    )),
    name := "ScalaTest",
    scalaVersion := "2.13.6",
    libraryDependencies += scalaTest
  )
