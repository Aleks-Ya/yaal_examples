package net;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class VerboseIT {

    @Test
    void printFtpCommands() throws IOException {
        var ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(System.out));
        ftp.connect(Rebex.HOST);
        ftp.login(Rebex.USER, Rebex.PASS);
        var names = ftp.listNames();
        ftp.logout();
        ftp.disconnect();
        assertThat(names).containsExactlyInAnyOrderElementsOf(Rebex.NAMES);
    }

}
