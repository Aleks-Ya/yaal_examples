import Dependencies.{scalaTestDep, testContainersScalaTestDep}

lazy val TestContainersScalaTest = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(scalaTestDep, testContainersScalaTestDep))
