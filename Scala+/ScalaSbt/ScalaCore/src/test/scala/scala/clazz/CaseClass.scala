package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.LocalDate

class CaseClass extends AnyFlatSpec with Matchers {

  it should "instantiate a case class" in {
    case class Book(isbn: String)
    val expIsbn = "978-0486282114"
    val book = Book(expIsbn)
    book.isbn shouldEqual expIsbn
  }

  it should "compare two a case class objects" in {
    case class Book(isbn: String)
    val isbn = "978-0486282114"
    val book1 = Book(isbn)
    val book2 = Book(isbn)
    book1 shouldBe book2
  }

  it should "copy a case class object with changed fields" in {
    case class Book(isbn: String, author: String)
    val isbn = "978-0486282114"
    val author1 = "King"
    val origin = Book(isbn, author1)
    val author2 = "Petrov"
    val copy = origin.copy(author = author2)
    origin.isbn shouldEqual isbn
    origin.author shouldEqual author1
    copy.isbn shouldEqual isbn
    copy.author shouldEqual author2
  }

  it should "use null in a case class" in {
    case class Book(id: Int, isbn: String, issued: LocalDate, count: Long)
    val book = Book(0, null, null, 0)
    book.isbn shouldBe null
  }

}
