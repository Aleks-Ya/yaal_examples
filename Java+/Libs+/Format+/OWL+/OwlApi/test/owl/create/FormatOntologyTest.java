package owl.create;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.N3DocumentFormat;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import owl.OwlFactory;

import java.io.ByteArrayOutputStream;

class FormatOntologyTest {
    private static final OWLOntology ontology = OwlFactory.createOntology();

    @Test
    void rdfXml() {
        System.out.println(saveToString(new RDFXMLDocumentFormat()));
    }

    @Test
    void owlXml() {
        System.out.println(saveToString(new OWLXMLDocumentFormat()));
    }

    @Test
    void obo() {
        System.out.println(saveToString(new OBODocumentFormat()));
    }

    @Test
    void manchester() {
        System.out.println(saveToString(new ManchesterSyntaxDocumentFormat()));
    }

    @Test
    void n3() {
        System.out.println(saveToString(new N3DocumentFormat()));
    }

    @Test
    void turtle() {
        System.out.println(saveToString(new TurtleDocumentFormat()));
    }

    private static String saveToString(OWLDocumentFormat format) {
        try {
            var os = new ByteArrayOutputStream();
            ontology.saveOntology(format, os);
            return os.toString();
        } catch (OWLOntologyStorageException e) {
            throw new RuntimeException(e);
        }
    }
}
