package wsclient

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.{Request, Server}
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.ws.WSClient
import play.api.test._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, DurationInt}

/**
 * Using WSClient as an HTTP client.
 * Docs: https://www.playframework.com/documentation/latest/ScalaWS
 */
class WsClientSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "WsClient GET" should {
    "get google.com page" in {
      val expBody = "the body"
      val expStatus = 201

      val server = new Server(0)
      val handler = new AbstractHandler() {
        override def handle(target: String, baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse): Unit = {
          response.getWriter.print(expBody)
          response.setStatus(expStatus)
          baseRequest.setHandled(true)
        }
      }
      server.setHandler(handler)
      server.start()

      val wsClient = app.injector.instanceOf[WSClient]
      wsClient must not be null

      val url = server.getURI.toString
      val futureResponse = wsClient.url(url)
        .withRequestTimeout(10000.millis)
        .withFollowRedirects(true)
        .get().map { response =>
        if (response.status == expStatus) {
          response.body
        } else {
          throw new RuntimeException("Wrong status code: " + response.status)
        }
      }
      Await.result(futureResponse, Duration.Inf)
      val actBody = futureResponse.value.get.get
      actBody mustEqual expBody
    }

  }
}
