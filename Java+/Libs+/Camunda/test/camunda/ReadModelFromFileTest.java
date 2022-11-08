package camunda;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.junit.jupiter.api.Test;

import java.io.File;

class ReadModelFromFileTest {

    @Test
    void file() {
        var file = new File(getClass().getResource("read_model_from_file.bpmn").getFile());
        var modelInstance = Bpmn.readModelFromFile(file);
        Bpmn.validateModel(modelInstance);
    }

    @Test
    void stream() {
        var stream = getClass().getResourceAsStream("read_model_from_file.bpmn");
        var modelInstance = Bpmn.readModelFromStream(stream);
        Bpmn.validateModel(modelInstance);
    }
}
