package scala.flow

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.LocalDateTime
import java.util.Locale

class MatchTest extends AnyFlatSpec with Matchers {

  it should "match" in {
    val code = "M"
    val gender = code match {
      case "M" => "man"
      case "W" => "women"
      case _ => "shit"
    }
    gender shouldEqual "man"
  }

  it should "throw exception from match" in {
    assertThrows[RuntimeException] {
      val code = "M"
      code match {
        case "M" => throw new RuntimeException("man " + code)
        case "W" => throw new IllegalArgumentException("women " + code)
        case _ => throw new NullPointerException("null " + code)
      }
    }
  }

  it should "compare with variables" in {
    val manCode = "M"
    val womenCode = "W"
    val code = "M"
    val gender = code match {
      case `manCode` => "man"
      case `womenCode` => "women"
      case _ => "shit"
    }
    gender shouldEqual "man"
  }

  it should "match class (use values)" in {
    def title(ref: AnyRef): String = ref match {
      case s: String => s"string: $s"
      case l: Locale => s"locale: $l"
      case _ => "shit"
    }

    title("abc") shouldEqual "string: abc"
    title(Locale.US) shouldEqual "locale: en_US"
    title(LocalDateTime.now()) shouldEqual "shit"
  }

  it should "match class (ignoring values)" in {
    def title(ref: AnyRef): String = ref match {
      case _: String => "string"
      case _: Locale => "locale"
      case _ => "shit"
    }

    title("abc") shouldEqual "string"
    title(Locale.US) shouldEqual "locale"
    title(LocalDateTime.now()) shouldEqual "shit"
  }

  it should "missing case" in {
    assertThrows[MatchError] {
      "X" match {
        case "M" => "man"
        case "W" => "women"
      }
    }
  }

  it should "nested match" in {
    val code = "M"
    val age = Some(20)
    val gender = code match {
      case "M" => "man"
      case "W" => "women"
      case _ => "shit"
    }
    gender shouldEqual "man"
  }

}
