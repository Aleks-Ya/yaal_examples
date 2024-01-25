package owl.read;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import owl.OwlFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OntologyFacadeTest {
    private final OWLOntology ontology = OwlFactory.readOntologyFromResource("owl/read/OntologyFacadeTest.obo");
    private final OntologyFacade facade = new OntologyFacade(ontology);
    private final IRI studentClassIri = IRI.create("http://example.com/Student");
    private final IRI teacherClassIri = IRI.create("http://example.com/Teacher");
    private final IRI personClassIri = IRI.create("http://example.com/Person");
    private final IRI idIri = IRI.create("http://www.geneontology.org/formats/oboInOwl#id");
    private final IRI labelIri = IRI.create("http://www.w3.org/2000/01/rdf-schema#label");
    private final IRI admissionYearIri = IRI.create("http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#admissionYear");
    private final IRI employmentYearIri = IRI.create("http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#employmentYear");
    private final IRI acceptApprovedIri = IRI.create("http://www.geneontology.org/formats/oboInOwl#acceptApproved");
    private final IRI responsiblePersonIri = IRI.create("http://www.geneontology.org/formats/oboInOwl#responsiblePerson");

    @Test
    void getAllAnnotationsValues() {
        var map = facade.getAllAnnotationsValues(studentClassIri);
        assertThat(map).containsExactlyInAnyOrderEntriesOf(Map.of(
                idIri, List.of("http://example.com/Student"),
                labelIri, List.of("University Student"),
                admissionYearIri, List.of("2015", "2021")
        ));
    }

    @Test
    void getAnnotationValues() {
        var values = facade.getAnnotationValues(studentClassIri, admissionYearIri);
        assertThat(values).containsExactlyInAnyOrder("2015", "2021");
    }

    @Test
    void getAllNestedAnnotationValues() {
        var values = facade.getAllNestedAnnotationValues(teacherClassIri, employmentYearIri);
        assertThat(values).containsExactlyInAnyOrder("the office", "the president", "Mark", "Rick");
    }

    @Test
    void getNestedAnnotationValues() {
        var values = facade.getNestedAnnotationValues(teacherClassIri, employmentYearIri, acceptApprovedIri);
        assertThat(values).containsExactlyInAnyOrder("the office", "the president");
    }

    @Test
    void getOuterAndNestedAnnotationValues() {
        var values = facade.getOuterAndNestedAnnotationValues(teacherClassIri, employmentYearIri, acceptApprovedIri);
        assertThat(values).containsExactlyInAnyOrder(
                new OntologyFacade.AnnotationValue(Optional.of("2005"), List.of("the office", "the president")));
    }

    @Test
    void getOuterAndNestedAnnotationValuesMap() {
        var values = facade.getOuterAndNestedAnnotationValuesMap(teacherClassIri, employmentYearIri);
        assertThat(values).containsExactlyInAnyOrder(
                new OntologyFacade.AnnotationValueMap(Optional.of("2005"),
                        Map.of(responsiblePersonIri, List.of("Mark", "Rick"),
                                acceptApprovedIri, List.of("the office", "the president"))));
    }

    @Test
    void isSubClassOf() {
        assertThat(facade.isSubClassOf(personClassIri, studentClassIri)).isTrue();
        assertThat(facade.isSubClassOf(teacherClassIri, studentClassIri)).isFalse();
    }

    @Test
    void getClassId() {
        assertThat(facade.getClassId(studentClassIri)).contains("http://example.com/Student");
        assertThat(facade.getClassId(teacherClassIri)).contains("http://example.com/Teacher");
        assertThat(facade.getClassId(personClassIri)).isEmpty();
    }

    @Test
    void getClassName() {
        assertThat(facade.getClassName(studentClassIri)).contains("University Student");
        assertThat(facade.getClassName(teacherClassIri)).isEmpty();
        assertThat(facade.getClassName(personClassIri)).isEmpty();
    }
}