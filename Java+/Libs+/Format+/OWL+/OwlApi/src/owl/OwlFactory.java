package owl;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import util.ResourceUtil;

public class OwlFactory {
    public static OWLOntology createOntology() {
        return createOntology(OWLManager.createOWLOntologyManager());
    }

    public static OWLOntology createOntology(OWLOntologyManager manager) {
        try {
            var ontologyId = new OWLOntologyID(IRI.create("http://example.com/ontologies/person.owl"));
            var ontology = manager.createOntology(ontologyId);
            var factory = manager.getOWLDataFactory();
            var person = factory.getOWLClass(IRI.create("http://example.com/Person"));
            var student = factory.getOWLClass(IRI.create("http://example.com/Student"));
            manager.addAxiom(ontology, factory.getOWLSubClassOfAxiom(student, person));
            return ontology;
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public static OWLOntology readOntologyFromResource(String resource) {
        try {
            var file = ResourceUtil.resourceToFile(resource);
            var manager = OWLManager.createOWLOntologyManager();
            return manager.loadOntologyFromOntologyDocument(file);
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }
}
