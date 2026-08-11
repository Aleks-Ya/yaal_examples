package net;

import org.apache.commons.net.ftp.FTPHTTPClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/* NOT WORKING: need to run a local proxy server */
class FTPHTTPClientIT {
    @Test
    void listNames() throws IOException {
        var ftp = new FTPHTTPClient("localhost", 2121);
        ftp.connect(Rebex.HOST);
        ftp.login(Rebex.USER, Rebex.PASS);
        var names = ftp.listNames();
        ftp.logout();
        ftp.disconnect();
        assertThat(names).containsExactlyInAnyOrderElementsOf(Rebex.NAMES);
    }
}
