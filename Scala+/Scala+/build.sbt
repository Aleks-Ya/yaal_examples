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
).aggregate(ScalaCore, ScalaTest, ScalaScopt, ScalaMock)

lazy val ScalaTest: Project = project.settings(
  commonSettings,
  libraryDependencies ++= Seq(scalaTestDep)
)

lazy val ScalaCore: Project = project.settings(
  commonSettings,
  libraryDependencies ++= Seq(scalaTestDep)
)

lazy val ScalaMock: Project = project.settings(
  commonSettings,
  libraryDependencies ++= Seq(scalaTestDep, scalaMockDep)
)

lazy val ScalaScopt: Project = project.settings(
  commonSettings,
  libraryDependencies ++= Seq(
    "com.github.scopt" % "scopt_2.12" % "3.7.1" % Compile,
    scalaTestDep
  )
)
