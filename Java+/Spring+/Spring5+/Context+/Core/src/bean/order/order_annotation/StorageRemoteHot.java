package bean.order.order_annotation;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
class StorageRemoteHot implements Storage {
    @Override
    public String getData() {
        return "remote hot data";
    }
}
