package bean.order.ordered_interface;

import org.springframework.core.Ordered;

interface Storage extends Ordered {
    String getData();
}
