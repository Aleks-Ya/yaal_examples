/**
 * Use "Scopt" librarty for parsing CLI arguments.
 */

import Dependencies._

lazy val scalaScopt = (project in file(".")).
  settings(
    name := "ScalaScopt",
    libraryDependencies ++= allDeps
  )