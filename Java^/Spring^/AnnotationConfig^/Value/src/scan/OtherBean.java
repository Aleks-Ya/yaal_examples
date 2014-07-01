package scan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;


@Component
public class OtherBean {

    /**
     * Другой бин.
     */
    @Value("#{dateFormat}")
    DateFormat dateFormat;

    @Override
    public String toString() {
        return String.format("OtherBean[dateFormat=%s]", dateFormat);
    }

}