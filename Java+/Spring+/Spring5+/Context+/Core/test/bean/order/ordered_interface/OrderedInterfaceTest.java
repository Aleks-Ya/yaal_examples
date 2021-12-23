package bean.order.ordered_interface;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Order beans with {@link org.springframework.core.Ordered} interface.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
class OrderedInterfaceTest {

    @Autowired
    private List<Storage> storages;

    @Autowired
    private StorageLocal storageLocal;

    @Autowired
    private StorageRemoteHot storageRemoteHot;

    @Autowired
    private StorageRemoteCold storageRemoteCold;

    @Test
    void order() {
        assertThat(storages).containsExactly(storageLocal, storageRemoteHot, storageRemoteCold);
    }
}