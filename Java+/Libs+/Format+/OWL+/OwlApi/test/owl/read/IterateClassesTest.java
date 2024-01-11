package owl.read;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.HasClassesInSignature;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import owl.OwlFactory;

import static org.assertj.core.api.Assertions.assertThat;

class IterateClassesTest {
    private final OWLOntology ontology = OwlFactory.readOntologyFromResource("owl/read/IterateTermsTest.obo");
    private final OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
    private final IRI studentClassIri = IRI.create("http://example.com/Student");
    private final IRI teacherClassIri = IRI.create("http://example.com/Teacher");
    private final IRI personClassIri = IRI.create("http://example.com/Person");
    private final IRI admissionYearIri = IRI.create("http://purl.obolibrary.org/obo/http://example.com/ontologies/person.owl#admissionYear");

    @Test
    void listClasses() {
        var classes = ontology.getClassesInSignature();
        assertThat(classes).map(OWLClass::toStringID).containsExactlyInAnyOrder(
                "http://example.com/Person", "http://example.com/Student", "http://example.com/Teacher");
    }

    @Test
    void getClassById() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var containsClass = ontology.containsClassInSignature(studentClassIri);
        assertThat(containsClass).isTrue();
        assertThat(studentClass).extracting(OWLClass::getIRI).isEqualTo(studentClassIri);
    }

    @Test
    void getAxiomsByClass() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var axioms = ontology.getAxioms(studentClass);
        assertThat(axioms).map(OWLClassAxiom::toString).contains(
                "SubClassOf(<http://example.com/Student> <http://example.com/Person>)",
                "DisjointClasses(<http://example.com/Student> <http://example.com/Teacher>)");
    }

    @Test
    void getSubClassAxiomsByClass() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var axioms = ontology.getSubClassAxiomsForSubClass(studentClass);
        assertThat(axioms).map(OWLClassAxiom::toString)
                .contains("SubClassOf(<http://example.com/Student> <http://example.com/Person>)");
    }

    @Test
    void getDisjointAxiomsByClass() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var axioms = ontology.getDisjointClassesAxioms(studentClass);
        assertThat(axioms).map(OWLClassAxiom::toString)
                .contains("DisjointClasses(<http://example.com/Student> <http://example.com/Teacher>)");
    }

    @Test
    void getAnnotationPropertyByIri() {
        var annotationProperty = factory.getOWLAnnotationProperty(admissionYearIri);
        assertThat(annotationProperty).extracting(OWLAnnotationProperty::getIRI).isEqualTo(admissionYearIri);
    }

    @Test
    void listAnnotationProperties() {
        var annotationProperties = ontology.annotationPropertiesInSignature();
        assertThat(annotationProperties).map(OWLAnnotationProperty::toString).containsExactlyInAnyOrder(
                "<http://purl.obolibrary.org/obo/http://example.com/ontologies/person.owl#admissionYear>",
                "<http://purl.obolibrary.org/obo/http://example.com/ontologies/person.owl#employmentYear>",
                "<http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion>",
                "<http://www.geneontology.org/formats/oboInOwl#id>",
                "rdfs:label");
    }

    @Test
    void getAnnotationProperty() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var annotationProperty = factory.getOWLAnnotationProperty(admissionYearIri);
        var annotations = EntitySearcher.getAnnotations(studentClass, ontology, annotationProperty).toList();
        assertThat(annotations).map(OWLAnnotation::toString).contains(
                "Annotation(<http://purl.obolibrary.org/obo/http://example.com/ontologies/person.owl#admissionYear> \"2021\"^^xsd:decimal)");
    }

    @Test
    void getAnnotationPropertyValue() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var annotationProperty = factory.getOWLAnnotationProperty(admissionYearIri);
        var annotations = EntitySearcher.getAnnotations(studentClass, ontology, annotationProperty).toList();
        assertThat(annotations).hasSize(1);
        var annotation = annotations.getFirst();
        var value1 = annotation.getValue().asLiteral().orElseThrow().getLiteral();
        var value2 = annotation.literalValue().orElseThrow().getLiteral();
        var expValue = "2021";
        assertThat(value1).isEqualTo(expValue);
        assertThat(value2).isEqualTo(expValue);
    }

    @Test
    void isSubClassOf_Reasoner() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var teacherClass = factory.getOWLClass(teacherClassIri);
        var personClass = factory.getOWLClass(personClassIri);

        var reasonerFactory = new StructuralReasonerFactory();
        var reasoner = reasonerFactory.createReasoner(ontology);
        var studentSuperClasses = reasoner.getSuperClasses(studentClass, true);

        boolean isStudentSubClassOfPerson = studentSuperClasses.containsEntity(personClass);
        assertThat(isStudentSubClassOfPerson).isTrue();

        boolean isStudentSubClassOfTeacher = studentSuperClasses.containsEntity(teacherClass);
        assertThat(isStudentSubClassOfTeacher).isFalse();
    }

    @Test
    void isSubClassOf_EntitySearcher() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var teacherClass = factory.getOWLClass(teacherClassIri);
        var personClass = factory.getOWLClass(personClassIri);
        var studentSuperClasses = EntitySearcher.getSuperClasses(studentClass, ontology)
                .flatMap(HasClassesInSignature::classesInSignature)
                .toList();

        boolean isStudentSubClassOfPerson = studentSuperClasses.contains(personClass);
        assertThat(isStudentSubClassOfPerson).isTrue();

        boolean isStudentSubClassOfTeacher = studentSuperClasses.contains(teacherClass);
        assertThat(isStudentSubClassOfTeacher).isFalse();
    }
}
