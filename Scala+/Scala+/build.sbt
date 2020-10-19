import Dependencies._
import sbt.Project


lazy val commonSettings = Seq(
    name := "Scala+",
    organization := "ru.yaal.examples.scala",
    version := "1",
    scalaVersion := "2.12.11",
    libraryDependencies ++= allDeps
)

lazy val root: Project = (project in file(".")).settings(
  commonSettings,
  name := "SbtMultiProject"
).aggregate(scalaCore, scalaScopt)


lazy val scalaCore: Project = (project in file("ScalaCore"))
  .settings(
    commonSettings
  )

lazy val scalaScopt: Project = (project in file("ScalaScopt"))
  .settings(
    commonSettings
  )
