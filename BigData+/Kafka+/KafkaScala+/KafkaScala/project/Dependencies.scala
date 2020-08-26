import sbt._

object Dependencies {
  val allDeps = Seq(
    "org.apache.kafka" % "kafka-clients" % "2.6.0",
    "org.slf4j" % "slf4j-simple" % "1.7.30"
  )
}
