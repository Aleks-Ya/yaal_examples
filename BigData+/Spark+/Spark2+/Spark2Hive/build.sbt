import Dependencies.{compileDeps, providedDeps, _}

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ru.yaal.examples.bigdata.spark2",
      scalaVersion := "2.11.12",
      version := "1"
    )),
    name := "Spark2Hive",
    libraryDependencies ++= compileDeps union providedDeps.map(_ % "provided") union testDeps,
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    parallelExecution in Test := false,
    assemblyJarName in assembly := "spark2_hive.jar",
    run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in(Compile, run), runner in(Compile, run))
  )

lazy val mainRunner = project.in(file("mainRunner")).dependsOn(RootProject(file("."))).settings(
  libraryDependencies ++= compileDeps union providedDeps union testDeps
)
