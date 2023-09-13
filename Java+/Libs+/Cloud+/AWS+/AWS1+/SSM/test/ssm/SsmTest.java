package ssm;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementAsyncClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SsmTest {
    @Test
    void getParameter() {
        var client = AWSSimpleSystemsManagementAsyncClientBuilder.defaultClient();
        var request = new GetParameterRequest().withName("card_number").withWithDecryption(true);
        var result = client.getParameter(request);
        var parameter = result.getParameter();
        var value = parameter.getValue();
        assertThat(value).isEqualTo("1234 5678 8900 000");
    }
}
