/*
 * SBT Multi Project build.
 * Docs: https://www.scala-sbt.org/1.x/docs/Multi-Project.html
 */

import Dependencies._
import sbt.Project


lazy val commonSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.11.12",
  version := "0.1.0-SNAPSHOT",
  libraryDependencies += scalaTest
)

lazy val root: Project = (project in file(".")).settings(
  commonSettings,
  name := "SbtMultiProject"
).aggregate(subProjectA, subProjectB)


lazy val subProjectA: Project = (project in file("SubProjectA"))
  .settings(
    commonSettings
  )

lazy val subProjectB: Project = (project in file("SubProjectB"))
  .settings(
    commonSettings
  )
