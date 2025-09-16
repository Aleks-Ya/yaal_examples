import Dependencies.{scalaTestDep, spark3StreamingDep}

//Structured Streaming is a part of Spark SQL.
lazy val Spark3Streaming = (project in file("."))
  .settings(libraryDependencies ++= Seq(spark3StreamingDep, scalaTestDep))