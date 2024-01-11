package owl.assertion;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;
import owl.OwlFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ToStringTest {
    @Test
    void ontology() {
        var ontology = OwlFactory.createOntology();
        assertThat(ontology)
                .hasToString("Ontology(OntologyID(OntologyIRI(<http://example.com/ontologies/person.owl>) " +
                        "VersionIRI(<null>))) [Axioms: 1 Logical Axioms: 1] First 20 axioms: " +
                        "{SubClassOf(<http://example.com/Student> <http://example.com/Person>) }");
    }

    @Test
    void ontologyId() {
        var emptyId = new OWLOntologyID();
        assertThat(emptyId).hasToString("OntologyID(Anonymous-0)");

        var withoutVersion = new OWLOntologyID(IRI.create("http://example.com/ontologies/animal.owl"));
        assertThat(withoutVersion).hasToString(
                "OntologyID(OntologyIRI(<http://example.com/ontologies/animal.owl>) VersionIRI(<null>))");

        var withVersion = new OWLOntologyID(
                IRI.create("http://example.com/ontologies/animal.owl"),
                IRI.create("http://example.com/data/ontology1/version2"));
        assertThat(withVersion).hasToString("OntologyID(OntologyIRI(<http://example.com/ontologies/animal.owl>) " +
                "VersionIRI(<http://example.com/data/ontology1/version2>))");
    }
}
