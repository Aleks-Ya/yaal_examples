/**
  * Use "Scopt" librarty for parsing CLI arguments.
  */

import Dependencies._

lazy val root = (project in file(".")).
  settings(
    name := "json4s",
    organization := "ru.yaal.scala",
    version := "1",
    scalaVersion := "2.12.7",
    libraryDependencies ++= allDeps
  )
