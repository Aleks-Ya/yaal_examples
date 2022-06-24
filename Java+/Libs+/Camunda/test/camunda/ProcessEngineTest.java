package camunda;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


public class ProcessEngineTest {

    @Test
    @Disabled("not finished")
    public void file() {
        ProcessEngineConfiguration config = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine engine = config.buildProcessEngine();

        RepositoryService repositoryService = engine.getRepositoryService();
        DeploymentBuilder deployment = repositoryService.createDeployment();

//        BpmnModelInstance modelInstance = Bpmn.createEmptyModel();
//        Definitions definitions = modelInstance.newInstance(Definitions.class);
//        definitions.setTargetNamespace("http://camunda.org/examples");
//        modelInstance.setDefinitions(definitions);

        BpmnModelInstance modelInstance = Bpmn.readModelFromStream(getClass().getResourceAsStream("read_model_from_file.bpmn"));
        Bpmn.validateModel(modelInstance);

        deployment.addModelInstance("model", modelInstance);

//        repositoryService.activateProcessDefinitionById("process-with-one-task");
        BpmnModelInstance modelFromDb = repositoryService.getBpmnModelInstance("model");
//        repositoryService.getProcessDefinition("process-with-one-task");
//        assertEquals(modelInstance, modelFromDb);

    }
}
