package jdkhttpclient

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.http4s.client.Client
import org.http4s.jdkhttpclient.JdkHttpClient
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JdkHttpClientAnyFlatSpecTest extends AnyFlatSpec with Matchers {
  private def withClient[A](f: Client[IO] => IO[A]): A = JdkHttpClient.simple[IO].use(f).unsafeRunSync()

  it should "send successful GET request" in {
    val statusCode = withClient { client =>
      client.get("https://http4s.org/") { response =>
        IO.pure(response.status.code)
      }
    }
    statusCode shouldEqual 200
  }

  it should "get correct status from URL" in {
    val statusCode = withClient(_.statusFromString("https://http4s.org/"))
    statusCode.code shouldEqual 200
  }

  it should "retrieve correct text body" in {
    val body = withClient(_.expect[String]("https://httpbin.org/base64/YWJjCg=="))
    body shouldEqual "abc\n"
  }

}