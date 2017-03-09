import Dependencies._

lazy val root = (project in file(".")).
  settings(
    name := "ScalaTest",
    organization := "ru.yaal.scala.scalatest",
    version := "1",
    scalaVersion := "2.11.8",
    libraryDependencies ++= allDeps
  )
