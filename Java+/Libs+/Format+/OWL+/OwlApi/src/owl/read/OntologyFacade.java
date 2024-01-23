package owl.read;

import org.semanticweb.owlapi.model.HasClassesInSignature;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.EntitySearcher;

import java.util.List;
import java.util.Optional;

public class OntologyFacade {
    private final OWLOntology ontology;
    private final OWLDataFactory factory;

    public OntologyFacade(OWLOntology ontology) {
        this.ontology = ontology;
        factory = ontology.getOWLOntologyManager().getOWLDataFactory();
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

    public boolean isSubClassOf(IRI parentClassIri, IRI childClassIri) {
        var childClass = factory.getOWLClass(childClassIri);
        var parentClass = factory.getOWLClass(parentClassIri);
        return EntitySearcher.getSuperClasses(childClass, ontology)
                .flatMap(HasClassesInSignature::classesInSignature)
                .toList().contains(parentClass);
    }
}
