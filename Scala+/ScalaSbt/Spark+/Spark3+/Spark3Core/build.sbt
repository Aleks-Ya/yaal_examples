import Dependencies.{spark3CoreDep, scalaTestDep}

lazy val akkaActorScalaExamples = (project in file(".")).
  settings(
    name := "spark3Core",
    libraryDependencies ++= Seq(spark3CoreDep, scalaTestDep)
  )