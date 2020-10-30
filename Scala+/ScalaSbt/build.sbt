import Dependencies._
import sbt.Project


lazy val common = Seq(
  organization := "ru.yaal.examples.scala",
  version := "1",
  scalaVersion := "2.12.12"
)

lazy val root: Project = (project in file(".")).settings(common, name := "ScalaSbt")
  .aggregate(ScalaCore, ScalaTest, ScalaMock, ScalaScopt, json4s, Slick, akkaActorScalaExamples, akkaQuickScala,
    playFrameworkExamples, playTest, playSlick, playLiquibase, playSlickLiquibase)

val deps = libraryDependencies

lazy val ScalaCore = project.settings(common, deps ++= Seq(scalaTestDep))
lazy val ScalaTest = project.settings(common)
lazy val ScalaMock = project.settings(common)
lazy val ScalaScopt = project.settings(common, deps ++= Seq("com.github.scopt" % "scopt_2.12" % "3.7.1", scalaTestDep))
lazy val json4s = project.settings(common, deps ++= Seq("org.json4s" % "json4s-native_2.12" % "3.6.4", scalaTestDep))
lazy val Slick = project.settings(common)
lazy val akkaActorScalaExamples = (project in file("Akka+/akka-actor-scala-examples")).settings(common)
lazy val akkaQuickScala = (project in file("Akka+/akka-quickstart-scala")).settings(common)
lazy val playFrameworkExamples = (project in file("PlayFramework+/play-framework-examples")).settings(common)
lazy val playTest = (project in file("PlayFramework+/play-test")).settings(common)
lazy val playSlick = (project in file("PlayFramework+/play-slick")).settings(common)
lazy val playLiquibase = (project in file("PlayFramework+/play-liquibase")).settings(common)
lazy val playJdbc = (project in file("PlayFramework+/play-jdbc")).settings(common)
lazy val playSlickLiquibase = (project in file("PlayFramework+/play-slick-liquibase")).settings(common)
