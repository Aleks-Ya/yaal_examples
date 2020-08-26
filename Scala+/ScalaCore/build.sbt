import Dependencies._

lazy val root = (project in file(".")).
  settings(
    name := "ScalaCore",
    organization := "ru.yaal.scala",
    version := "1",
    scalaVersion := "2.12.11",
    libraryDependencies ++= allDeps
  )
