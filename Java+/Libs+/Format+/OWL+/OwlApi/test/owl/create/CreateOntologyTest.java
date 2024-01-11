package owl.create;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.io.ByteArrayOutputStream;

class CreateOntologyTest {
    @Test
    void create() throws OWLOntologyCreationException, OWLOntologyStorageException {
        var manager = OWLManager.createOWLOntologyManager();
        var ontology = manager.createOntology();
        var factory = manager.getOWLDataFactory();
        var person = factory.getOWLClass(IRI.create("http://example.com/Person"));
        var student = factory.getOWLClass(IRI.create("http://example.com/Student"));
        manager.addAxiom(ontology, factory.getOWLSubClassOfAxiom(student, person));
        var os = new ByteArrayOutputStream();
        var format = new RDFXMLDocumentFormat();
        manager.saveOntology(ontology, format, os);
        System.out.println(os);
    }
}
