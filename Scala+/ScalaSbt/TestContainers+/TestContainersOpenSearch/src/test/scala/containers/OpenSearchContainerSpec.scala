package containers

import com.dimafeng.testcontainers.OpensearchContainer
import com.dimafeng.testcontainers.OpensearchContainer.defaultImage
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import org.apache.http.HttpHost
import org.opensearch.client.{RequestOptions, RestClient, RestHighLevelClient}
import org.scalatest.flatspec._
import org.scalatest.matchers.should.Matchers
import org.testcontainers.utility.DockerImageName

class OpenSearchContainerSpec extends AnyFlatSpec with TestContainerForAll with Matchers {
  private val OpenSearchVersion = "2.19.3"

  override val containerDef = OpensearchContainer.Def(DockerImageName.parse(s"$defaultImage:$OpenSearchVersion"))

  it should "start OpenSearch" in {
    withContainers { container =>
      val builder = RestClient.builder(new HttpHost(container.host, container.firstMappedPort, "http"))
      val client = new RestHighLevelClient(builder)
      val info = client.info(RequestOptions.DEFAULT)
      info.getClusterName shouldEqual "docker-cluster"
      info.getVersion.getNumber shouldEqual OpenSearchVersion
    }
  }
}