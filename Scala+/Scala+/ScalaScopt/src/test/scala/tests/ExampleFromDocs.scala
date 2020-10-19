package tests

import java.io.File

import org.scalatest.{FlatSpec, Matchers}
import scopt.OptionParser

/**
  * Source: https://github.com/scopt/scopt/tree/v3.7.1
  */
class ExampleFromDocs extends FlatSpec with Matchers {

  it should "run example from docs" in {
    case class Config(foo: Int = -1, out: File = new File("."), xyz: Boolean = false,
                      libName: String = "", maxCount: Int = -1, verbose: Boolean = false, debug: Boolean = false,
                      mode: String = "", files: Seq[File] = Seq(), keepalive: Boolean = false,
                      jars: Seq[File] = Seq(), kwargs: Map[String, String] = Map())

    val parser = new OptionParser[Config]("scopt") {
      head("scopt", "3.x")

      opt[Int]('f', "foo").action((x, c) =>
        c.copy(foo = x)).text("foo is an integer property")

      opt[File]('o', "out").required().valueName("<file>").
        action((x, c) => c.copy(out = x)).
        text("out is a required file property")

      opt[(String, Int)]("max").action({
        case ((k, v), c) => c.copy(libName = k, maxCount = v)
      }).
        validate(x =>
          if (x._2 > 0) success
          else failure("Value <max> must be >0")).
        keyValueName("<libname>", "<max>").
        text("maximum count for <libname>")

      opt[Seq[File]]('j', "jars").valueName("<jar1>,<jar2>...").action((x, c) =>
        c.copy(jars = x)).text("jars to include")

      opt[Map[String, String]]("kwargs").valueName("k1=v1,k2=v2...").action((x, c) =>
        c.copy(kwargs = x)).text("other arguments")

      opt[Unit]("verbose").action((_, c) =>
        c.copy(verbose = true)).text("verbose is a flag")

      opt[Unit]("debug").hidden().action((_, c) =>
        c.copy(debug = true)).text("this option is hidden in the usage text")

      help("help").text("prints this usage text")

      arg[File]("<file>...").unbounded().optional().action((x, c) =>
        c.copy(files = c.files :+ x)).text("optional unbounded args")

      note("some notes.")

      cmd("update").action((_, c) => c.copy(mode = "update")).
        text("update is a command.").
        children(
          opt[Unit]("not-keepalive").abbr("nk").action((_, c) =>
            c.copy(keepalive = false)).text("disable keepalive"),
          opt[Boolean]("xyz").action((x, c) =>
            c.copy(xyz = x)).text("xyz is a boolean property"),
          opt[Unit]("debug-update").hidden().action((_, c) =>
            c.copy(debug = true)).text("this option is hidden in the usage text"),
          checkConfig(c =>
            if (c.keepalive && c.xyz) failure("xyz cannot keep alive")
            else success)
        )
    }

    val outValueExp = "abc"
    val args = List("applicatoin", "--out", outValueExp)

    var outValueAct: File = null
    parser.parse(args, Config()) match {
      case Some(config) =>
        println("config: " + config)
        outValueAct = config.out

      case None =>
        println("None")
    }

    outValueAct.getName shouldEqual outValueExp
  }

}
