package jdkhttpclient

import cats.effect.IO
import cats.effect.kernel.Resource
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.client.Client
import org.http4s.jdkhttpclient.JdkHttpClient
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import java.net.http.HttpClient

class ProvidedHttpClientTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {

  "JdkHttpClient" - {
    "send GET request" in {
      val javaHttpClient = HttpClient.newBuilder().build()
      val jdkHttpClient = JdkHttpClient[IO](javaHttpClient)
      val clientResource = Resource.pure[IO, Client[IO]](jdkHttpClient)
      clientResource.use(_.get("https://http4s.org/")(response => IO(response.status.code)))
        .asserting(_ shouldEqual 200)
    }

  }
}