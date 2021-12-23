package bean.order.order_annotation;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
class StorageLocal implements Storage {
    @Override
    public String getData() {
        return "local data";
    }
}
