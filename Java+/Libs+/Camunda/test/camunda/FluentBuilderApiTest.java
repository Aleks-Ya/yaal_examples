package camunda;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.jupiter.api.Test;

import java.io.File;


public class FluentBuilderApiTest {

    @Test
    public void name() throws Exception {
        BpmnModelInstance modelInstance = Bpmn.createProcess()
                .name("Example process")
                .executable()
                .startEvent()
                .userTask()
                .name("Some work to do")
                .endEvent()
                .done();

        // validate and write model to file
        Bpmn.validateModel(modelInstance);

        File file = File.createTempFile("FluentBuilderApiTest-", ".bpmn");
        System.out.println(file.getAbsolutePath());
        Bpmn.writeModelToFile(file, modelInstance);
    }
}
