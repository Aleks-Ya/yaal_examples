package bean.order.ordered_interface;

import org.springframework.stereotype.Component;

@Component
class StorageRemoteHot implements Storage {
    @Override
    public String getData() {
        return "remote hot data";
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
