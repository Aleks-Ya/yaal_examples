import Dependencies._
import sbt.Project


lazy val common = Seq(
  organization := "ru.yaal.examples.scala",
  version := "1",
  scalaVersion := "2.12.12"
)

lazy val root: Project = (project in file(".")).settings(common, name := "ScalaSbt")
  .aggregate(ScalaCore, ScalaTest, ScalaScopt, ScalaMock, json4s, Slick, akkaActorScalaExamples, akkaQuickScala,
    playFrameworkExamples)

val deps = libraryDependencies

lazy val ScalaTest = project.settings(common, deps ++= Seq(scalaTestDep))
lazy val ScalaCore = project.settings(common, deps ++= Seq(scalaTestDep))
lazy val ScalaMock = project.settings(common, deps ++= Seq(scalaTestDep, scalaMockDep))
lazy val ScalaScopt = project.settings(common, deps ++= Seq("com.github.scopt" % "scopt_2.12" % "3.7.1", scalaTestDep))
lazy val json4s = project.settings(common, deps ++= Seq("org.json4s" % "json4s-native_2.12" % "3.6.4", scalaTestDep))
lazy val Slick = project.settings(common,
  deps ++= Seq("com.typesafe.slick" %% "slick" % "3.3.3",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
    "com.h2database" % "h2" % "1.4.200",
    scalaTestDep)
)
lazy val akkaActorScalaExamples = (project in file("Akka+/akka-actor-scala-examples")).settings(common)
lazy val akkaQuickScala = (project in file("Akka+/akka-quickstart-scala")).settings(common)
lazy val playFrameworkExamples = (project in file("PlayFramework+/play-framework-examples")).settings(common)
