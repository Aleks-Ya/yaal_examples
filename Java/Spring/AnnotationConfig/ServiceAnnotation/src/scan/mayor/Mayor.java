package scan.mayor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Мэр.
 */
@Service("mayor")
public class Mayor {
    @Value("Полтавченко")
    private String fio;

    @Autowired
    @Qualifier("rightHand")
    private MayorAssistant rightHand;

    @Autowired
    @Qualifier("leftHand")
    private MayorAssistant leftHand;

    @Override
    public String toString() {
        return String.format("Mayor[fio='%s' rightHand='%s' leftHand='%s']", fio, rightHand, leftHand);
    }
}
