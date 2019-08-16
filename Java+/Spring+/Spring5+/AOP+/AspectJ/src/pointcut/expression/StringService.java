package pointcut.expression;

import org.springframework.stereotype.Service;

@Service
class StringService {

    String toUpperCase(String str) {
        return str.toUpperCase();
    }

}
