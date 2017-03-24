import sbt._

object Dependencies {
  val allDeps = Seq(
    /*"org.scala-lang" % "scala-library" % "2.11.8" % Provided,*/
    "org.apache.kafka" % "kafka-clients" % "0.10.2.0" % Provided,
    "org.apache.kafka" % "kafka_2.11" % "0.10.2.0",
    "org.scalatest" % "scalatest_2.11" % "3.0.1" % Test,
    "net.manub" % "scalatest-embedded-kafka_2.11" % "0.12.0" % Test
  )
}
