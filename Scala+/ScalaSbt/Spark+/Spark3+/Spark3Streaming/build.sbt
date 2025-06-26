import Dependencies.{scalaTestDep, spark3StreamingDep}

lazy val Spark3Streaming = (project in file("."))
  .settings(libraryDependencies ++= Seq(spark3StreamingDep, scalaTestDep))