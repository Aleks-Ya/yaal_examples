import Dependencies._
import sbt.Project


lazy val commonSettings = Seq(
  organization := "ru.yaal.examples.scala",
  version := "1",
  scalaVersion := "2.12.11"
)

lazy val root: Project = (project in file(".")).settings(
  commonSettings,
  name := "ScalaExamples"
).aggregate(scalaCore, scalaTest, scalaScopt)

lazy val scalaTest: Project = (project in file("ScalaTest")).settings(
  commonSettings,
  libraryDependencies ++= Seq(scalaTestDep)
)

lazy val scalaCore: Project = (project in file("ScalaCore")).settings(
  commonSettings,
  libraryDependencies ++= Seq(scalaTestDep, scalaMockDep)
)

lazy val scalaScopt: Project = (project in file("ScalaScopt")).settings(
  commonSettings,
  libraryDependencies ++= Seq(
    "com.github.scopt" % "scopt_2.12" % "3.7.1" % Compile,
    scalaTestDep
  )
)
