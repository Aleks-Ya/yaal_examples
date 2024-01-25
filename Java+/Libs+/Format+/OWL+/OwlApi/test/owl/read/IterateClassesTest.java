package owl.read;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.HasClassesInSignature;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import owl.OwlFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class IterateClassesTest {
    private final OWLOntology ontology = OwlFactory.readOntologyFromResource("owl/read/IterateTermsTest.obo");
    private final OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
    private final IRI studentClassIri = IRI.create("http://example.com/Student");
    private final IRI teacherClassIri = IRI.create("http://example.com/Teacher");
    private final IRI personClassIri = IRI.create("http://example.com/Person");
    private final IRI admissionYearIri = IRI.create("http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#admissionYear");
    private final IRI employmentYearIri = IRI.create("http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#employmentYear");
    private final IRI referenceLinkIri = IRI.create("http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#referenceLink");

    @Test
    void listClasses() {
        var classes = ontology.getClassesInSignature();
        assertThat(classes).map(OWLClass::toStringID).containsExactlyInAnyOrder(
                "http://example.com/Person", "http://example.com/Student", "http://example.com/Teacher");
    }

    @Test
    void getClassByIri() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var containsClass = ontology.containsClassInSignature(studentClassIri);
        assertThat(containsClass).isTrue();
        assertThat(studentClass).extracting(OWLClass::getIRI).isEqualTo(studentClassIri);
    }

    @Test
    void getAxiomsByClass() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var axioms = ontology.getAxioms(studentClass);
        assertThat(axioms).map(OWLClassAxiom::toString).containsExactlyInAnyOrder(
                "SubClassOf(<http://example.com/Student> <http://example.com/Person>)",
                "DisjointClasses(<http://example.com/Student> <http://example.com/Teacher>)");
    }

    @Test
    void getSubClassAxiomsByClass() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var axioms = ontology.getSubClassAxiomsForSubClass(studentClass);
        assertThat(axioms).map(OWLClassAxiom::toString).containsExactlyInAnyOrder(
                "SubClassOf(<http://example.com/Student> <http://example.com/Person>)");
    }

    @Test
    void getDisjointAxiomsByClass() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var axioms = ontology.getDisjointClassesAxioms(studentClass);
        assertThat(axioms).map(OWLClassAxiom::toString).containsExactlyInAnyOrder(
                "DisjointClasses(<http://example.com/Student> <http://example.com/Teacher>)");
    }

    @Test
    void getAnnotationPropertyByIri() {
        var annotationProperty = factory.getOWLAnnotationProperty(admissionYearIri);
        assertThat(annotationProperty).extracting(OWLAnnotationProperty::toStringID).isEqualTo(
                "http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#admissionYear");
    }

    @Test
    void listAnnotationProperties() {
        var annotationProperties = ontology.annotationPropertiesInSignature();
        assertThat(annotationProperties).map(OWLAnnotationProperty::toStringID).containsExactlyInAnyOrder(
                "http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#admissionYear",
                "http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#employmentYear",
                "http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#birthYear",
                "http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#referenceLink",
                "http://www.geneontology.org/formats/oboInOwl#acceptApproved",
                "http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion",
                "http://www.geneontology.org/formats/oboInOwl#id",
                "http://www.w3.org/2000/01/rdf-schema#label");
    }

    @Test
    void getAnnotationsOnClass() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var annotationProperty = factory.getOWLAnnotationProperty(admissionYearIri);
        var annotations = EntitySearcher.getAnnotations(studentClass, ontology, annotationProperty).toList();
        assertThat(annotations).map(OWLAnnotation::toString).containsExactlyInAnyOrder(
                "Annotation(<http://purl.obolibrary.org/obo/http://example.com/ontologies/person.obo#admissionYear> \"2021\"^^xsd:decimal)");
    }

    @Test
    void getAnnotationValues() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var annotationProperty = factory.getOWLAnnotationProperty(admissionYearIri);
        var annotations = EntitySearcher.getAnnotations(studentClass, ontology, annotationProperty).toList();
        var values = annotations.stream()
                .map(OWLAnnotation::literalValue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(OWLLiteral::getLiteral)
                .toList();
        assertThat(values).containsExactlyInAnyOrder("2021");
    }

    @Test
    void isSubClassOf_Reasoner() {
        var studentClass = factory.getOWLClass(studentClassIri);
        var teacherClass = factory.getOWLClass(teacherClassIri);
        var personClass = factory.getOWLClass(personClassIri);

        var reasonerFactory = new StructuralReasonerFactory();
        var reasoner = reasonerFactory.createReasoner(ontology);
        var studentSuperClasses = reasoner.getSuperClasses(studentClass, true);

        var isStudentSubClassOfPerson = studentSuperClasses.containsEntity(personClass);
        assertThat(isStudentSubClassOfPerson).isTrue();

        var isStudentSubClassOfTeacher = studentSuperClasses.containsEntity(teacherClass);
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

        var isStudentSubClassOfPerson = studentSuperClasses.contains(personClass);
        assertThat(isStudentSubClassOfPerson).isTrue();

        var isStudentSubClassOfTeacher = studentSuperClasses.contains(teacherClass);
        assertThat(isStudentSubClassOfTeacher).isFalse();
    }

    @Test
    void getNestedAnnotationValues() {
        var clazz = factory.getOWLClass(teacherClassIri);
        var classAnnotationAssertionAxioms = EntitySearcher.getAnnotationAssertionAxioms(clazz, ontology).toList();
        assertThat(classAnnotationAssertionAxioms).hasSize(4);
        var outerAnnotations = classAnnotationAssertionAxioms.stream()
                .filter(a -> a.getProperty().getIRI().equals(employmentYearIri)).toList();
        var nestedAnnotations = outerAnnotations.stream().flatMap(a -> a.getAnnotations().stream()).toList();
        var nestedAnnotationsValues = nestedAnnotations.stream()
                .map(OWLAnnotation::literalValue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(OWLLiteral::getLiteral)
                .toList();
        assertThat(nestedAnnotationsValues).containsExactlyInAnyOrder("the office");
    }

    @Test
    void getNestedAnnotationValues2() {
        var clazz = factory.getOWLClass(teacherClassIri);
        var classAnnotationAssertionAxioms = EntitySearcher.getAnnotationAssertionAxioms(clazz, ontology).toList();
        assertThat(classAnnotationAssertionAxioms).hasSize(4);
        var outerAnnotationProperty = factory.getOWLAnnotationProperty(employmentYearIri);
        var outerAnnotations = classAnnotationAssertionAxioms.stream()
                .filter(a -> a.getProperty().equals(outerAnnotationProperty)).toList();
        var nestedAnnotations = outerAnnotations.stream().flatMap(a -> a.getAnnotations().stream()).toList();
        var nestedAnnotationsValues = nestedAnnotations.stream()
                .map(OWLAnnotation::literalValue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(OWLLiteral::getLiteral)
                .toList();
        assertThat(nestedAnnotationsValues).containsExactlyInAnyOrder("the office");
    }

    @Test
    void getIdAnnotationValues() {
        var clazz = factory.getOWLClass(teacherClassIri);
        var annotationProperty = factory.getOWLAnnotationProperty(referenceLinkIri);
        var annotations = EntitySearcher.getAnnotations(clazz, ontology, annotationProperty).toList();
        var values = annotations.stream()
                .map(OWLAnnotation::annotationValue)
                .map(OWLAnnotationValue::asIRI)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        assertThat(values).containsExactlyInAnyOrder(studentClassIri);
    }
}
