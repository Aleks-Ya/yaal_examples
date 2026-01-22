package toml

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import toml.Value.{Num, Tbl}


class HelloTest extends AnyFlatSpec with Matchers {

  it should "parse AST" in {
    val res = toml.Toml.parse("a = 1")
    res shouldEqual Right(Tbl(Map("a" -> Num(1))))
  }

  it should "parse table" in {
    case class Table(b: Int)
    case class Root(a: Int, table: Table)

    val table =
      """
        |a = 1
        |[table]
        |b = 2
      """.stripMargin
    import toml.derivation.auto._ //It needed
    val res = Toml.parseAs[Root](table) //It works
    res shouldEqual Right(Root(1, Table(2)))
  }

}
