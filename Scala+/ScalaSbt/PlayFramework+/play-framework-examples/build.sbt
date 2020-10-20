lazy val playFrameworkExamples = (project in file(".")).
  settings(
    name := "play-framework-examples",
    connectInput in run := true,
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    )
  ).enablePlugins(PlayScala)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "ru.yaal.examples.scala.play.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "ru.yaal.examples.scala.play.binders._"
