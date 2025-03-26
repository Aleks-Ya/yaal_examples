import Dependencies.{dbUtilsApiDep, hadoopClientApiDep, scalaTestDep}

lazy val DbUtils = (project in file("."))
  .settings(libraryDependencies ++= Seq(
    scalaTestDep, dbUtilsApiDep,
    hadoopClientApiDep //for `val dbfs: FileSystem = DBUtilsHolder.dbutils.fs.dbfs`
  ))