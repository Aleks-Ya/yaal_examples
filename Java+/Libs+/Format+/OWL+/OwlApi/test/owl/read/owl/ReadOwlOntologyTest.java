package owl.read.owl;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import owl.OwlFactory;
import util.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class ReadOwlOntologyTest {
    @Test
    void rdfXml() throws OWLOntologyCreationException {
        var file = ResourceUtil.resourceToFile(getClass(), "ReadOntologyTest_act.rdf");
        var manager = OWLManager.createOWLOntologyManager();
        var actOntology = manager.loadOntologyFromOntologyDocument(file);
        var expOntology = OwlFactory.readOntologyFromResource("owl/read/ReadOntologyTest_exp.rdf");
        assertThat(actOntology).isEqualTo(expOntology);
    }

    @Test
    void obo() throws OWLOntologyCreationException {
        var file = ResourceUtil.resourceToFile(getClass(), "ReadOntologyTest_act.obo");
        var manager = OWLManager.createOWLOntologyManager();
        var actOntology = manager.loadOntologyFromOntologyDocument(file);
        var expOntology = OwlFactory.readOntologyFromResource("owl/read/ReadOntologyTest_exp.rdf");
        assertThat(actOntology).isEqualTo(expOntology);
    }
}
