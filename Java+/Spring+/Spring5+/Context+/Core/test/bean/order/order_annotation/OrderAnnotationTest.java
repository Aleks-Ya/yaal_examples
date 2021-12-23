package bean.order.order_annotation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Order beans with @{@link org.springframework.core.annotation.Order} annotation.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
class OrderAnnotationTest {

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