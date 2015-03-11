package scan.mayor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MayorAssistantFactory {

    @Bean(name = "rightHand")
    public MayorAssistant getRightHand() {
        return new MayorAssistant("Экономистов В.П.");
    }

    @Bean(name = "leftHand")
    public MayorAssistant getLeftHand() {
        return new MayorAssistant("Криворуков Ж.Х.");
    }
}
