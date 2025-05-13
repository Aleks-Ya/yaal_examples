package owl.create;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class CreateOntologyTest {
    @Test
    void createProgrammatically() throws OWLOntologyCreationException, OWLOntologyStorageException {
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

    @Test
    void createFromString() throws OWLOntologyCreationException {
        var owl = """
                <?xml version="1.0"?>
                <rdf:RDF
                        xml:base="http://purl.obolibrary.org/obo/ro/core.owl"
                        xmlns:owl="http://www.w3.org/2002/07/owl#"
                        xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                        xmlns:xml="http://www.w3.org/XML/1998/namespace">
                    <owl:Ontology rdf:about="http://purl.obolibrary.org/obo/ro/core.owl">
                        <owl:versionIRI rdf:resource="http://my.ontology.com/core.owl"/>
                    </owl:Ontology>
                </rdf:RDF>
                """.stripIndent();
        var documentSource = new StringDocumentSource(owl);
        var manager = OWLManager.createOWLOntologyManager();
        var ontology = manager.loadOntologyFromOntologyDocument(documentSource);
        var versionIri = ontology.getOntologyID().getVersionIRI();
        assertThat(versionIri).contains(IRI.create("http://my.ontology.com/core.owl"));
    }
}
