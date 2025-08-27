package net;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class FTPClientIT {
    @Test
    void listNames() throws IOException {
        var ftp = new FTPClient();
        ftp.connect("test.rebex.net");
        ftp.login("demo", "password");
        var names = ftp.listNames();
        System.out.println(Arrays.toString(names));
        ftp.logout();
        ftp.disconnect();
        assertThat(names).isNotEmpty();
    }
}
