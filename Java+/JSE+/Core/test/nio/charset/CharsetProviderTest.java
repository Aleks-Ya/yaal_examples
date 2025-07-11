package nio.charset;

import org.junit.jupiter.api.Test;

import java.nio.charset.spi.CharsetProvider;
import java.util.ServiceLoader;

import static org.assertj.core.api.Assertions.assertThat;

class CharsetProviderTest {

    @Test
    void loadCharsetProvider() {
        var loader = ServiceLoader.load(CharsetProvider.class);
        var providers = loader.stream().toList();
        assertThat(providers).hasSize(1);
        assertThat(providers.getFirst().getClass().getName()).isEqualTo("java.util.ServiceLoader$ProviderImpl");
    }

}
