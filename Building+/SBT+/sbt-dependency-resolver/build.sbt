import Dependencies._

ThisBuild / scalaVersion := "2.12.11"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "ru.yaal.examples.sbt"
ThisBuild / organizationName := "YaalExamples"

lazy val root = (project in file("."))
  .settings(
    name := "sbt-dependency-resolver",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.slf4j" % "slf4j-api" % "2.0.0-alpha1"
  )

externalResolvers := Seq(
  Resolver.mavenCentral,
  Resolver.mavenLocal
)
