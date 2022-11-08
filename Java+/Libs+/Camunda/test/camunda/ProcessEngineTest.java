package camunda;

import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ProcessEngineTest {

    @Test
    @Disabled("not finished")
    public void file() {
        var config = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        var engine = config.buildProcessEngine();

        var repositoryService = engine.getRepositoryService();
        var deployment = repositoryService.createDeployment();

//        BpmnModelInstance modelInstance = Bpmn.createEmptyModel();
//        Definitions definitions = modelInstance.newInstance(Definitions.class);
//        definitions.setTargetNamespace("http://camunda.org/examples");
//        modelInstance.setDefinitions(definitions);

        var modelInstance = Bpmn.readModelFromStream(getClass().getResourceAsStream("read_model_from_file.bpmn"));
        Bpmn.validateModel(modelInstance);

        deployment.addModelInstance("model", modelInstance);

//        repositoryService.activateProcessDefinitionById("process-with-one-task");
        var modelFromDb = repositoryService.getBpmnModelInstance("model");
//        repositoryService.getProcessDefinition("process-with-one-task");
//        assertEquals(modelInstance, modelFromDb);

    }
}
