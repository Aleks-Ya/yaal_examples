import Dependencies.{embeddedKafkaDep, scalaTestDep, spark3StreamingDep, sparkStreamingKafkaDep}

lazy val Spark3Streaming = (project in file("."))
  .settings(libraryDependencies ++= Seq(spark3StreamingDep, scalaTestDep, sparkStreamingKafkaDep, embeddedKafkaDep))