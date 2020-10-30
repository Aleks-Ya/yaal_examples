import Dependencies.{akkaActorTestKitTypedDep, akkaActorTypedDep, logbackClassicDep, scalaTestDep}

lazy val akkaActorScalaExamples = (project in file(".")).
  settings(
    name := "akka-actor-scala-examples",
    libraryDependencies ++= Seq(akkaActorTypedDep, akkaActorTestKitTypedDep, logbackClassicDep, scalaTestDep)
  )