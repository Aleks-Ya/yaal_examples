package security.sasl;

import org.junit.jupiter.api.Test;

import java.security.Security;

class MechanismsTest {

    @Test
    void serverSupportedMechanisms() {
        printMechanisms("SaslServerFactory");
    }

    @Test
    void clientSupportedMechanisms() {
        printMechanisms("SaslClientFactory");
    }

    private void printMechanisms(String type) {
        System.out.println("Type: " + type);
        var providers = Security.getProviders();
        for (var provider : providers) {
            var mechanisms = provider.keySet().stream().map(key -> (String) key)
                    .filter(key -> key.startsWith(type + "."))
                    .map(key -> key.replace(type + ".", ""))
                    .toList();
            if (!mechanisms.isEmpty()) {
                System.out.printf("Provider: %s, Mechanisms: %s\n", provider.getName(), mechanisms);
            }
        }
        System.out.println();
    }
}
