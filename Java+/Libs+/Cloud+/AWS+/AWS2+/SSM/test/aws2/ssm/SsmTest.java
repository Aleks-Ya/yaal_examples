package aws2.ssm;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;

import static org.assertj.core.api.Assertions.assertThat;

class SsmTest {
    @Test
    void getParameter() {
        try (var ssm = SsmClient.create()) {
            var request = GetParameterRequest.builder().name("card_number").withDecryption(true).build();
            var response = ssm.getParameter(request);
            var parameter = response.parameter();
            var value = parameter.value();
            assertThat(value).isEqualTo("1234 5678 8900 000");
        }
    }
}
