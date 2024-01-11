package owl.assertion;

import org.junit.jupiter.api.Test;
import owl.OwlFactory;

import static org.assertj.core.api.Assertions.assertThat;

class EqualsTest {
    @Test
    void ontology() {
        var ontology1 = OwlFactory.createOntology();
        var ontology2 = OwlFactory.createOntology();
        assertThat(ontology1).isEqualTo(ontology2);
    }
}
