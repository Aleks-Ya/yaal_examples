/*
 * SBT Multi Project build.
 * Docs: https://www.scala-sbt.org/1.x/docs/Multi-Project.html
 */

import Dependencies.*

lazy val commonBuildSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.12.21",
  version := "0.1.0-SNAPSHOT",
  libraryDependencies += scalaTest
)
inThisBuild(commonBuildSettings)

lazy val root: Project = (project in file(".")).settings(
  name := "SbtMultiProject"
).aggregate(subProjectA, subProjectB)


lazy val subProjectA = (project in file("SubProjectA"))
  .settings(
    name := "SubProjectA",
    libraryDependencies += scalaTest
  )

lazy val subProjectB = (project in file("SubProjectB"))
  .settings(
    name := "SubProjectB",
    libraryDependencies += scalaTest
  )