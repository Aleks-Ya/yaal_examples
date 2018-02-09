package pointcut.execution.interface_method_call;

import org.springframework.stereotype.Component;

@Component
class ConverterImpl implements ConverterInterface {
    @Override
    public String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}
