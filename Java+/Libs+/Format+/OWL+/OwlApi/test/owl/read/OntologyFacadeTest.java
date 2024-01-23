package owl.read;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import owl.OwlFactory;

import static org.assertj.core.api.Assertions.assertThat;

class OntologyFacadeTest {
    private final OWLOntology ontology = OwlFactory.readOntologyFromResource("owl/read/OntologyFacadeTest.obo");
    private final OntologyFacade facade = new OntologyFacade(ontology);
    private final IRI studentClassIri = IRI.create("http://example.com/Student");
    private final IRI teacherClassIri = IRI.create("http://example.com/Teacher");
    private final IRI personClassIri = IRI.create("http://example.com/Person");
    private final IRI admissionYearIri = IRI.create("http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#admissionYear");
    private final IRI employmentYearIri = IRI.create("http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#employmentYear");
    private final IRI acceptApprovedIri = IRI.create("http://www.geneontology.org/formats/oboInOwl#acceptApproved");

    @Test
    void getAnnotationValues() {
        var values = facade.getAnnotationValues(studentClassIri, admissionYearIri);
        assertThat(values).containsExactlyInAnyOrder("2015", "2021");
    }

    @Test
    void getNestedAnnotationValues() {
        var values = facade.getNestedAnnotationValues(teacherClassIri, employmentYearIri, acceptApprovedIri);
        assertThat(values).containsExactlyInAnyOrder("the office", "the president");
    }

    @Test
    void isSubClassOf() {
        assertThat(facade.isSubClassOf(personClassIri, studentClassIri)).isTrue();
        assertThat(facade.isSubClassOf(teacherClassIri, studentClassIri)).isFalse();
    }
}