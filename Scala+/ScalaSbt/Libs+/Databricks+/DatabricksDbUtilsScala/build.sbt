import Dependencies.{dbUtilsApiDep, hadoopClientApiDep, scalaTestDep, databricksDbUtilsScalaDep}

lazy val DatabricksDbUtilsScala = (project in file("."))
  .settings(libraryDependencies ++= Seq(
    scalaTestDep, databricksDbUtilsScalaDep,
    hadoopClientApiDep //for `val dbfs: FileSystem = DBUtilsHolder.dbutils.fs.dbfs`
  ))