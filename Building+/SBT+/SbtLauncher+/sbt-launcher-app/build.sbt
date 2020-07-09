import Dependencies._

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "sbt-launcher-app",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.scala-sbt" % "launcher-interface" % "1.1.4" % "provided",
    resolvers += sbtResolver.value
  )
