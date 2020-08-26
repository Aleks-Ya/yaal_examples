import Dependencies._

/**
 * Produce and consume to Kafka server started according "Quick Start"
 * (https://kafka.apache.org/quickstart)
 */
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ru.yaal.examples.bigdata.kafka",
      scalaVersion := "2.13.3",
      version := "1"
    )),
    name := "KafkaScala",
    libraryDependencies ++= allDeps
  )
