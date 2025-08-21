import Dependencies.{logbackClassicDep, opensearchRestHighLevelClientDep, scalaTestDep, testContainersopenSearchDep, testContainersScalaTestDep}

lazy val TestContainersOpenSearch = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(scalaTestDep, testContainersScalaTestDep, testContainersopenSearchDep,
    opensearchRestHighLevelClientDep, logbackClassicDep))
