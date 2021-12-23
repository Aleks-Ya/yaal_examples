package bean.order.ordered_interface;

import org.springframework.stereotype.Component;

@Component
class StorageLocal implements Storage {
    @Override
    public String getData() {
        return "local data";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
