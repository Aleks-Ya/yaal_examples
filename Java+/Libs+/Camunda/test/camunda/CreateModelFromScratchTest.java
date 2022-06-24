package camunda;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.junit.jupiter.api.Test;

import java.io.File;


/**
 * Создание модели в java-коде и сохранение ее в bpmn-файл.
 */
public class CreateModelFromScratchTest {

    @Test
    void name() throws Exception {
        BpmnModelInstance modelInstance = Bpmn.createEmptyModel();

        Definitions definitions = modelInstance.newInstance(Definitions.class);
        definitions.setTargetNamespace("http://camunda.org/examples");
        modelInstance.setDefinitions(definitions);

        // create the process
        Process process = createElement(definitions, "process-with-one-task", Process.class, modelInstance);

        // create start event, user task and end event
        StartEvent startEvent = createElement(process, "start", StartEvent.class, modelInstance);
        UserTask task1 = createElement(process, "task1", UserTask.class, modelInstance);
        task1.setName("User Task");
        EndEvent endEvent = createElement(process, "end", EndEvent.class, modelInstance);

        // create the connections between the elements
        createSequenceFlow(process, startEvent, task1, modelInstance);
        createSequenceFlow(process, task1, endEvent, modelInstance);

        // validate and write model to file
        Bpmn.validateModel(modelInstance);
        File file = File.createTempFile(getClass().getSimpleName() + "-", ".bpmn");
        System.out.println(file.getAbsolutePath());
        Bpmn.writeModelToFile(file, modelInstance);
    }

    private <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement,
                                                                 String id,
                                                                 Class<T> elementClass,
                                                                 BpmnModelInstance modelInstance) {
        T element = modelInstance.newInstance(elementClass);
        element.setAttributeValue("id", id, true);
        parentElement.addChildElement(element);
        return element;
    }

    private SequenceFlow createSequenceFlow(Process process, FlowNode from, FlowNode to, BpmnModelInstance modelInstance) {
        String identifier = from.getId() + "-" + to.getId();
        SequenceFlow sequenceFlow = createElement(process, identifier, SequenceFlow.class, modelInstance);
        process.addChildElement(sequenceFlow);
        sequenceFlow.setSource(from);
        from.getOutgoing().add(sequenceFlow);
        sequenceFlow.setTarget(to);
        to.getIncoming().add(sequenceFlow);
        return sequenceFlow;
    }
}
