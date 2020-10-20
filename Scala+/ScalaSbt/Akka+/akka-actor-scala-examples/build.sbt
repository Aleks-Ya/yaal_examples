import Dependencies.scalaTestDep

lazy val akkaVersion = "2.6.6"

lazy val akkaActorScalaExamples = (project in file(".")).
  settings(
    name := "akka-actor-scala-examples",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      scalaTestDep
    )
  )