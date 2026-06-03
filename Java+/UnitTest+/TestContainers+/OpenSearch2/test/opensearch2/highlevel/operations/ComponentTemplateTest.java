package opensearch2.highlevel.operations;

import opensearch2.OpenSearchExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.PutComponentTemplateRequest;
import org.opensearch.cluster.metadata.ComponentTemplate;
import org.opensearch.cluster.metadata.Template;
import org.opensearch.common.compress.CompressedXContent;
import org.opensearch.common.settings.Settings;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.opensearch.client.RequestOptions.DEFAULT;

@ExtendWith(OpenSearchExtension.class)
class ComponentTemplateTest {

    @Test
    void createEmptyComponentTemplate(RestHighLevelClient client) throws IOException {
        var template = new Template(null, null, Collections.emptyMap());
        var componentTemplate = new ComponentTemplate(template, null, Collections.emptyMap());
        var request = new PutComponentTemplateRequest();
        request.name("empty_component_template");
        request.componentTemplate(componentTemplate);
        var response = client.cluster().putComponentTemplate(request, DEFAULT);
        assertThat(response.isAcknowledged()).isTrue();
    }

    @Test
    void createComponentTemplate(RestHighLevelClient client) throws IOException {
        var settings = Settings.builder()
                .put("number_of_shards", 1)
                .put("number_of_replicas", 0)
                .build();
        var mappings = new CompressedXContent("""
                {
                  "properties": {
                    "title": {
                      "type": "text"
                    },
                    "age": {
                      "type": "integer"
                    }
                  }
                }
                """);
        var template = new Template(settings, mappings, Collections.emptyMap());
        var componentTemplate = new ComponentTemplate(template, null, Collections.emptyMap());
        var request = new PutComponentTemplateRequest();
        request.name("component_template1");
        request.componentTemplate(componentTemplate);
        var response = client.cluster().putComponentTemplate(request, DEFAULT);
        assertThat(response.isAcknowledged()).isTrue();
    }

}