package camunda;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;


public class ReadModelFromFileTest {

    @Test
    public void file() {
        File file = new File(getClass().getResource("read_model_from_file.bpmn").getFile());
        BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);
        Bpmn.validateModel(modelInstance);
    }

    @Test
    public void stream() {
        InputStream stream = getClass().getResourceAsStream("read_model_from_file.bpmn");
        BpmnModelInstance modelInstance = Bpmn.readModelFromStream(stream);
        Bpmn.validateModel(modelInstance);
    }
}
