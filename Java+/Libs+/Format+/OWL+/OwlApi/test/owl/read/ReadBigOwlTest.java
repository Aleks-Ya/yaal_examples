package owl.read;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Requires downloaded file: https://purl.obolibrary.org/obo/go/extensions/go-plus.owl")
class ReadBigOwlTest {
    private static final File file = new File("/home/aleks/Downloads/go-plus.owl");

    @Test
    void loadOntology() throws OWLOntologyCreationException {
        var manager = OWLManager.createOWLOntologyManager();
        var ontology = manager.loadOntologyFromOntologyDocument(file);
        assertThat(ontology.getAxiomCount()).isEqualTo(875010);
    }
}
