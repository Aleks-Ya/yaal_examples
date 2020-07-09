package app

import java.io.File

import xsbti.{AppConfiguration, AppMain, ApplicationID, MainResult}

class LauncherApp extends AppMain {

  override def run(configuration: AppConfiguration): MainResult = {
    val scalaVersion = configuration.provider.scalaProvider.version
    println("Hello world!  Running Scala " + scalaVersion)
    configuration.arguments.foreach(println)

    // demonstrate the ability to reboot the application into different versions of Scala and how to return the code to
    // exit with
    scalaVersion match {
      case "2.10.6" =>
        new xsbti.Reboot {
          def arguments: Array[String] = configuration.arguments

          def baseDirectory: File = configuration.baseDirectory

          def scalaVersion = "2.11.8"

          def app: ApplicationID = configuration.provider.id
        }
      case "2.11.8" => new Exit(1)
      case _ => new Exit(0)
    }
  }

  class Exit(val code: Int) extends xsbti.Exit

}