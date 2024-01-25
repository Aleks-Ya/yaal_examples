package owl.read;

import org.semanticweb.owlapi.model.HasAnnotationValue;
import org.semanticweb.owlapi.model.HasClassesInSignature;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.EntitySearcher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class OntologyFacade {
    private final OWLOntology ontology;
    private final OWLDataFactory factory;
    private final IRI idIri = IRI.create("http://www.geneontology.org/formats/oboInOwl#id");
    private final IRI labelIri = IRI.create("http://www.w3.org/2000/01/rdf-schema#label");

    public OntologyFacade(OWLOntology ontology) {
        this.ontology = ontology;
        factory = ontology.getOWLOntologyManager().getOWLDataFactory();
    }

    public Optional<String> getClassId(IRI classIri) {
        return getAnnotationValues(classIri, idIri).stream().findFirst();
    }

    public Optional<String> getClassName(IRI classIri) {
        return getAnnotationValues(classIri, labelIri).stream().findFirst();
    }

    public Map<IRI, List<String>> getAllAnnotationsValues(IRI classIri) {
        var clazz = factory.getOWLClass(classIri);
        return EntitySearcher.getAnnotations(clazz, ontology)
                .collect(groupingBy(a -> a.getProperty().getIRI())).entrySet().stream()
                .map(e -> Map.entry(e.getKey(), e.getValue().stream()
                        .map(HasAnnotationValue::literalValue)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(OWLLiteral::getLiteral)
                        .sorted()
                        .toList()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<String> getAnnotationValues(IRI classIri, IRI annotationIri) {
        var clazz = factory.getOWLClass(classIri);
        var annotationProperty = factory.getOWLAnnotationProperty(annotationIri);
        return EntitySearcher.getAnnotations(clazz, ontology, annotationProperty)
                .map(OWLAnnotation::literalValue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(OWLLiteral::getLiteral)
                .toList();
    }

    public List<String> getNestedAnnotationValues(IRI classIri, IRI annotationIri, IRI nestedAnnotationIri) {
        var clazz = factory.getOWLClass(classIri);
        return EntitySearcher.getAnnotationAssertionAxioms(clazz, ontology)
                .filter(a -> a.getProperty().getIRI().equals(annotationIri))
                .flatMap(a -> a.getAnnotations().stream())
                .filter(a -> a.getProperty().getIRI().equals(nestedAnnotationIri))
                .map(OWLAnnotation::literalValue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(OWLLiteral::getLiteral)
                .toList();
    }

    public List<String> getAllNestedAnnotationValues(IRI classIri, IRI annotationIri) {
        var clazz = factory.getOWLClass(classIri);
        return EntitySearcher.getAnnotationAssertionAxioms(clazz, ontology)
                .filter(a -> a.getProperty().getIRI().equals(annotationIri))
                .flatMap(a -> a.getAnnotations().stream())
                .map(a -> a.literalValue().map(OWLLiteral::getLiteral))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public List<AnnotationValue> getOuterAndNestedAnnotationValues(IRI classIri, IRI annotationIri, IRI nestedAnnotationIri) {
        var clazz = factory.getOWLClass(classIri);
        return EntitySearcher.getAnnotationAssertionAxioms(clazz, ontology)
                .filter(a -> a.getProperty().getIRI().equals(annotationIri))
                .map(axiom -> {
                    var value = axiom.literalValue().map(OWLLiteral::getLiteral);
                    var nestedAnnotationValues = axiom.getAnnotations().stream()
                            .filter(a -> a.getProperty().getIRI().equals(nestedAnnotationIri))
                            .map(a -> a.literalValue().map(OWLLiteral::getLiteral))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .toList();
                    return new AnnotationValue(value, nestedAnnotationValues);
                }).toList();
    }

    public List<AnnotationValueMap> getOuterAndNestedAnnotationValuesMap(IRI classIri, IRI annotationIri) {
        var clazz = factory.getOWLClass(classIri);
        return EntitySearcher.getAnnotationAssertionAxioms(clazz, ontology)
                .filter(a -> a.getProperty().getIRI().equals(annotationIri))
                .map(axiom -> {
                    var value = axiom.literalValue().map(OWLLiteral::getLiteral);
                    var map = axiom.getAnnotations().stream()
                            .collect(groupingBy(a -> a.getProperty().getIRI()))
                            .entrySet().stream()
                            .map(e -> Map.entry(e.getKey(), e.getValue().stream()
                                    .map(HasAnnotationValue::literalValue)
                                    .filter(Optional::isPresent)
                                    .map(Optional::get)
                                    .map(OWLLiteral::getLiteral)
                                    .toList()))
                            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
                    return new AnnotationValueMap(value, map);
                }).toList();
    }

    public boolean isSubClassOf(IRI parentClassIri, IRI childClassIri) {
        var childClass = factory.getOWLClass(childClassIri);
        var parentClass = factory.getOWLClass(parentClassIri);
        return EntitySearcher.getSuperClasses(childClass, ontology)
                .flatMap(HasClassesInSignature::classesInSignature)
                .toList().contains(parentClass);
    }

    public record AnnotationValue(Optional<String> annotationValue, List<String> nestedAnnotationValues) {
    }

    public record AnnotationValueMap(Optional<String> annotationValue, Map<IRI, List<String>> nestedAnnotationValues) {
    }
}
