package net;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.apache.commons.net.ftp.FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE;
import static org.apache.commons.net.ftp.FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE;
import static org.assertj.core.api.Assertions.assertThat;

class DataConnectionModeIT {

    @Test
    void activeMode() throws IOException {
        var ftp = new FTPClient();
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);

        ftp.connect(Rebex.HOST);
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);

        ftp.login(Rebex.USER, Rebex.PASS);
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);

        var names = ftp.listNames();
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);

        ftp.logout();
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);
        ftp.disconnect();
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);

        assertThat(names).containsExactlyInAnyOrderElementsOf(Rebex.NAMES);
    }

    @Test
    void passiveMode() throws IOException {
        var ftp = new FTPClient();
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);

        ftp.connect(Rebex.HOST);
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);

        ftp.login(Rebex.USER, Rebex.PASS);
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);

        ftp.enterLocalPassiveMode();
        assertThat(ftp.getDataConnectionMode()).isEqualTo(PASSIVE_LOCAL_DATA_CONNECTION_MODE);

        var names = ftp.listNames();
        assertThat(ftp.getDataConnectionMode()).isEqualTo(PASSIVE_LOCAL_DATA_CONNECTION_MODE);

        ftp.logout();
        assertThat(ftp.getDataConnectionMode()).isEqualTo(PASSIVE_LOCAL_DATA_CONNECTION_MODE);

        ftp.disconnect();
        assertThat(ftp.getDataConnectionMode()).isEqualTo(ACTIVE_LOCAL_DATA_CONNECTION_MODE);

        assertThat(names).containsExactlyInAnyOrderElementsOf(Rebex.NAMES);
    }

}
