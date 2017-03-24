import sbt._

object Dependencies {
  val allDeps = Seq(
    "org.apache.kafka" % "kafka-clients" % "0.10.2.0",
    "org.slf4j" % "slf4j-simple" % "1.7.24"
  )
}
