package kuber;

import com.google.gson.Gson;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import util.InputStreamUtil;

import java.io.IOException;

public class ClientFactory {
    private static final int SUCCESS_EXIT_CODE = 0;
    private static final LazyInitializer<Console.ClusterConfig> clusterConfig = new LazyInitializer<>() {
        @Override
        protected Console.ClusterConfig initialize() {
            return getClusterConfig();
        }
    };
    private static final LazyInitializer<OpenShiftClient> developerClient = new LazyInitializer<>() {
        @Override
        protected OpenShiftClient initialize() {
            try {
                var config = clusterConfig.get();
                return createClient(config.url, config.developerCredentials);
            } catch (ConcurrentException e) {
                throw new RuntimeException(e);
            }
        }
    };
    private static final LazyInitializer<OpenShiftClient> adminClient = new LazyInitializer<>() {
        @Override
        protected OpenShiftClient initialize() {
            try {
                var config = clusterConfig.get();
                return createClient(config.url, config.adminCredentials);
            } catch (ConcurrentException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static OpenShiftClient getDeveloperClient() {
        try {
            return developerClient.get();
        } catch (ConcurrentException e) {
            throw new RuntimeException(e);
        }
    }

    public static OpenShiftClient createAdminClient() {
        try {
            return adminClient.get();
        } catch (ConcurrentException e) {
            throw new RuntimeException(e);
        }
    }

    private static OpenShiftClient createClient(String url, Credentials credentials) {
        var config = new ConfigBuilder()
                .withMasterUrl(url)
                .withUsername(credentials.username)
                .withPassword(credentials.password)
                .build();
        return new DefaultOpenShiftClient(config);
    }

    private static class Credentials {
        String username;
        String password;
    }

    private static class Console {
        private static class ClusterConfig {
            String url;
            Credentials adminCredentials;
            Credentials developerCredentials;
        }

        ClusterConfig clusterConfig;
    }

    private static Console.ClusterConfig getClusterConfig() {
        try {
            var pb = new ProcessBuilder("/media/aleks/ADATA/installed/crc_open_shift/crc-linux-1.30.1-amd64/crc", "console", "-o", "json");
            var process = pb.start();
            var exitCode = process.waitFor();
            if (exitCode != SUCCESS_EXIT_CODE) {
                var error = InputStreamUtil.inputStreamToString(process.getErrorStream());
                throw new RuntimeException("Exit code: " + exitCode + ", stderr: " + error);
            }
            var json = InputStreamUtil.inputStreamToString(process.getInputStream());
            var console = new Gson().fromJson(json, Console.class);
            return console.clusterConfig;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
