import Dependencies.{logbackClassicDep, opensearchRestHighLevelClientDep, scalaTestDep, testContainersOpenSearchDep, testContainersScalaTestDep}

lazy val TestContainersOpenSearch = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(scalaTestDep, testContainersScalaTestDep, testContainersOpenSearchDep,
    opensearchRestHighLevelClientDep, logbackClassicDep))
