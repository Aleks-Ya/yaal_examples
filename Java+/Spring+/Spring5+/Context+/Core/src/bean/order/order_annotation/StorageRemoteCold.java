package bean.order.order_annotation;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
class StorageRemoteCold implements Storage {
    @Override
    public String getData() {
        return "remote cold data";
    }
}
