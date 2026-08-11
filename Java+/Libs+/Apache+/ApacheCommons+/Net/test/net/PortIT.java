package net;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PortIT {

    @Test
    void portsInActiveMode() throws IOException {
        var ftp = new FTPClient();
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThatThrownBy(ftp::getLocalPort).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(ftp::getRemotePort).isInstanceOf(NullPointerException.class);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        ftp.connect(Rebex.HOST);
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThat(ftp.getLocalPort()).isPositive();
        assertThat(ftp.getRemotePort()).isEqualTo(21);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        ftp.login(Rebex.USER, Rebex.PASS);
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThat(ftp.getLocalPort()).isPositive();
        assertThat(ftp.getRemotePort()).isEqualTo(21);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        var names = ftp.listNames();
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThat(ftp.getLocalPort()).isPositive();
        assertThat(ftp.getRemotePort()).isEqualTo(21);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        ftp.logout();
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThat(ftp.getLocalPort()).isPositive();
        assertThat(ftp.getRemotePort()).isEqualTo(21);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        ftp.disconnect();
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThatThrownBy(ftp::getLocalPort).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(ftp::getRemotePort).isInstanceOf(NullPointerException.class);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        assertThat(names).containsExactlyInAnyOrderElementsOf(Rebex.NAMES);
    }

    @Test
    void portsInPassiveMode() throws IOException {
        var ftp = new FTPClient();
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThatThrownBy(ftp::getLocalPort).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(ftp::getRemotePort).isInstanceOf(NullPointerException.class);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        ftp.connect(Rebex.HOST);
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThat(ftp.getLocalPort()).isPositive();
        assertThat(ftp.getRemotePort()).isEqualTo(21);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        ftp.login(Rebex.USER, Rebex.PASS);
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThat(ftp.getLocalPort()).isPositive();
        assertThat(ftp.getRemotePort()).isEqualTo(21);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        ftp.enterLocalPassiveMode();
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThat(ftp.getLocalPort()).isPositive();
        assertThat(ftp.getRemotePort()).isEqualTo(21);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        var names = ftp.listNames();
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThat(ftp.getLocalPort()).isPositive();
        assertThat(ftp.getRemotePort()).isEqualTo(21);
        assertThat(ftp.getPassivePort()).isPositive();

        ftp.logout();
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThat(ftp.getLocalPort()).isPositive();
        assertThat(ftp.getRemotePort()).isEqualTo(21);
        assertThat(ftp.getPassivePort()).isPositive();

        ftp.disconnect();
        assertThat(ftp.getDefaultPort()).isEqualTo(21);
        assertThatThrownBy(ftp::getLocalPort).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(ftp::getRemotePort).isInstanceOf(NullPointerException.class);
        assertThat(ftp.getPassivePort()).isEqualTo(-1);

        assertThat(names).containsExactlyInAnyOrderElementsOf(Rebex.NAMES);
    }

}
