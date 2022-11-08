package camunda;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.junit.jupiter.api.Test;

import java.io.File;

class FluentBuilderApiTest {

    @Test
    void name() throws Exception {
        var modelInstance = Bpmn.createProcess()
                .name("Example process")
                .executable()
                .startEvent()
                .userTask()
                .name("Some work to do")
                .endEvent()
                .done();

        // validate and write model to file
        Bpmn.validateModel(modelInstance);

        var file = File.createTempFile("FluentBuilderApiTest-", ".bpmn");
        System.out.println(file.getAbsolutePath());
        Bpmn.writeModelToFile(file, modelInstance);
    }
}
