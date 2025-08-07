package containers

import com.dimafeng.testcontainers.GenericContainer
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import org.scalatest.flatspec._
import org.testcontainers.containers.wait.strategy.Wait

import java.net.URL
import scala.io.Source

class GenericContainerSpec extends AnyFlatSpec with TestContainerForAll {

  override val containerDef = GenericContainer.Def(
    "nginx:latest",
    exposedPorts = Seq(80),
    waitStrategy = Wait.forHttp("/")
  )

  "GenericContainer" should "start nginx and expose 80 port" in {
    withContainers { nginxContainer =>
      assert(
        Source
          .fromInputStream(
            new URL(s"http://${nginxContainer.containerIpAddress}:${nginxContainer.mappedPort(80)}/")
              .openConnection().getInputStream
          )
          .mkString.contains("If you see this page, the nginx web server is successfully installed")
      )
    }
  }
}