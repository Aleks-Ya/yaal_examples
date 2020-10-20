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
).aggregate(ScalaCore, ScalaTest, ScalaScopt, ScalaMock, json4s, Slick, akkaActorScalaExamples, akkaQuickScala)

lazy val ScalaTest: Project = project.settings(commonSettings, libraryDependencies ++= Seq(scalaTestDep))
lazy val ScalaCore: Project = project.settings(commonSettings, libraryDependencies ++= Seq(scalaTestDep))
lazy val ScalaMock: Project = project.settings(commonSettings, libraryDependencies ++= Seq(scalaTestDep, scalaMockDep))
lazy val ScalaScopt: Project = project.settings(commonSettings,
  libraryDependencies ++= Seq("com.github.scopt" % "scopt_2.12" % "3.7.1", scalaTestDep)
)
lazy val json4s: Project = project.settings(commonSettings,
  libraryDependencies ++= Seq("org.json4s" % "json4s-native_2.12" % "3.6.4", scalaTestDep)
)
lazy val Slick: Project = project.settings(commonSettings,
  libraryDependencies ++= Seq("com.typesafe.slick" %% "slick" % "3.3.3",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
    "com.h2database" % "h2" % "1.4.200",
    scalaTestDep)
)
lazy val akkaActorScalaExamples: Project = (project in file("Akka+/akka-actor-scala-examples")).settings(commonSettings)
lazy val akkaQuickScala: Project = (project in file("Akka+/akka-quickstart-scala")).settings(commonSettings)
