import sbt._

object Dependencies {
  private val kafkaVersion = "3.2.0"
  val allDeps: Seq[ModuleID] = Seq(
    "org.apache.kafka" % "kafka-clients" % kafkaVersion % Provided,
    "org.apache.kafka" %% "kafka" % kafkaVersion,
    "org.scalatest" %% "scalatest" % "3.2.12" % Test,
    "io.github.embeddedkafka" %% "embedded-kafka" % kafkaVersion % Test,
    "org.slf4j" % "slf4j-nop" % "1.7.30"
  )
}
