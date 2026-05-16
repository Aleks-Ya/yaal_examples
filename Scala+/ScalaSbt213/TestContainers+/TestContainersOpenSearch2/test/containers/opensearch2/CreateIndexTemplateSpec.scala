package containers.opensearch2

import com.dimafeng.testcontainers.OpensearchContainer
import com.dimafeng.testcontainers.OpensearchContainer.defaultImage
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import org.apache.http.HttpHost
import org.opensearch.client.indices.{GetIndexTemplatesRequest, PutIndexTemplateRequest}
import org.opensearch.client.{RequestOptions, RestClient, RestHighLevelClient}
import org.opensearch.common.settings.Settings
import org.opensearch.common.xcontent.XContentType
import org.opensearch.core.xcontent.MediaTypeRegistry
import org.scalatest.flatspec._
import org.scalatest.matchers.should.Matchers
import org.testcontainers.utility.DockerImageName

class CreateIndexTemplateSpec extends AnyFlatSpec with TestContainerForAll with Matchers {
  private val openSearchVersion = "2.19.5"

  override val containerDef = OpensearchContainer.Def(DockerImageName.parse(s"$defaultImage:$openSearchVersion"))

  it should "create and verify index template" in withContainers { container =>
    val builder = RestClient.builder(new HttpHost(container.host, container.firstMappedPort, "http"))
    val client = new RestHighLevelClient(builder)

    val templateName = "test-template"
    val request = new PutIndexTemplateRequest(templateName)
    request.patterns(java.util.Arrays.asList("test-*"))
    request.settings(Settings.builder()
      .put("index.number_of_shards", 1)
      .put("index.number_of_replicas", 0))
    request.mapping(
      """{
        |  "properties": {
        |    "message": {
        |      "type": "text"
        |    },
        |    "timestamp": {
        |      "type": "date"
        |    }
        |  }
        |}""".stripMargin,
      XContentType.JSON
    )

    client.indices().putTemplate(request, RequestOptions.DEFAULT)

    val getRequest = new GetIndexTemplatesRequest(templateName)
    val response = client.indices().getIndexTemplate(getRequest, RequestOptions.DEFAULT)
    response.getIndexTemplates should have size 1
    response.getIndexTemplates.get(0).name() shouldEqual templateName
    response.getIndexTemplates.get(0).patterns() should contain("test-*")
  }

  it should "create index template from JSON" in withContainers { container =>
    val builder = RestClient.builder(new HttpHost(container.host, container.firstMappedPort, "http"))
    val client = new RestHighLevelClient(builder)

    val templateName = "json-template"
    val templateJson =
      """{
        |  "index_patterns": ["json-*"],
        |  "settings": {
        |    "number_of_shards": 2,
        |    "number_of_replicas": 1
        |  },
        |  "mappings": {
        |    "properties": {
        |      "user": {
        |        "type": "keyword"
        |      },
        |      "content": {
        |        "type": "text"
        |      }
        |    }
        |  }
        |}""".stripMargin

    val request = new PutIndexTemplateRequest(templateName)
    request.source(templateJson, MediaTypeRegistry.JSON)

    client.indices().putTemplate(request, RequestOptions.DEFAULT)

    val getRequest = new GetIndexTemplatesRequest(templateName)
    val response = client.indices().getIndexTemplate(getRequest, RequestOptions.DEFAULT)
    response.getIndexTemplates should have size 1
    response.getIndexTemplates.get(0).name() shouldEqual templateName
    response.getIndexTemplates.get(0).patterns() should contain("json-*")
  }

}