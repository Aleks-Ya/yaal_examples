import Dependencies.{scalaTestDep, spark3CoreDep}

lazy val Spark3Core = (project in file(".")).settings(libraryDependencies ++= Seq(spark3CoreDep, scalaTestDep))
