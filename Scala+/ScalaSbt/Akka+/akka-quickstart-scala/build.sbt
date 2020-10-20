import Dependencies.scalaTestDep
//Source: https://developer.lightbend.com/guides/akka-quickstart-scala/

lazy val akkaVersion = "2.6.6"

lazy val akkaQuickScala = (project in file(".")).
  settings(
    name := "akka-quickstart-scala",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      scalaTestDep
    )
  )
