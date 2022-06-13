package security.sasl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.sasl.AuthorizeCallback;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SaslTest {

    @Test
    @Disabled("Not work")
    void test() throws SaslException {
        var mechanisms = new String[]{"PLAIN"};
        var clientCallbackHandler = new ClientCallbackHandler();
        var serverName = "server1";
        var protocol = "SASL_SSL";
        var sc = Sasl.createSaslClient(
                mechanisms, null, protocol, serverName, Map.of(), clientCallbackHandler);


        var serverCallbackHandler = new ServerCallbackHandler();
        var mechanism = "PLAIN";
        var properties = Map.<String, String>of();
        var ss = Sasl.createSaslServer(mechanism, protocol, serverName, properties, serverCallbackHandler);

        byte[] challenge = ss.evaluateResponse(new byte[0]);
        byte[] response = sc.evaluateChallenge(challenge);
        challenge = ss.evaluateResponse(response);
        response = sc.evaluateChallenge(challenge);

        assertThat(ss.isComplete()).isTrue();
        assertThat(sc.isComplete()).isTrue();
    }

    public static class ServerCallbackHandler implements CallbackHandler {
        @Override
        public void handle(Callback[] cbs) {
            for (var cb : cbs) {
                if (cb instanceof AuthorizeCallback ac) {
                    ac.setAuthorized(true); //Perform application-specific authorization action
                } else if (cb instanceof NameCallback nc) {
                    nc.setName("username"); //Collect username in application-specific manner
                } else if (cb instanceof PasswordCallback pc) {
                    pc.setPassword("password".toCharArray()); //Collect password in application-specific manner
                } else if (cb instanceof RealmCallback rc) {
                    rc.setText("myServer"); //Collect realm data in application-specific manner
                }
            }
        }
    }

    public static class ClientCallbackHandler implements CallbackHandler {
        @Override
        public void handle(Callback[] cbs) {
            for (var cb : cbs) {
                if (cb instanceof NameCallback nc) {
                    nc.setName("username"); //Collect username in application-specific manner
                } else if (cb instanceof PasswordCallback pc) {
                    pc.setPassword("password".toCharArray()); //Collect password in application-specific manner
                } else if (cb instanceof RealmCallback rc) {
                    rc.setText("myServer"); //Collect realm data in application-specific mannero
                }
            }
        }
    }
}
