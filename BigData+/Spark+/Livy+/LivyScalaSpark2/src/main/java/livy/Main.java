package livy;

import org.apache.livy.LivyClient;
import org.apache.livy.LivyClientBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        LivyClient client = new LivyClientBuilder()
                .setURI(URI.create("http://spark-standalone-cluster-livy:8998"))
                .build();

        try {
            String piJar = "target/scala-2.11/livyscala_2.11-1.jar";
            System.err.printf("Uploading %s to the Spark context...\n", piJar);
            client.uploadJar(new File(piJar).getAbsoluteFile()).get();

            int samples = 5;
            System.err.printf("Running PiJob with %d samples...\n", samples);
            double pi = client.submit(new PiJob(samples)).get();

            System.out.println("Pi is roughly: " + pi);
        } finally {
            client.stop(true);
        }

    }
}
