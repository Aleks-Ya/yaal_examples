package bean.order.ordered_interface;

import org.springframework.stereotype.Component;

@Component
class StorageRemoteCold implements Storage {
    @Override
    public String getData() {
        return "remote cold data";
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
