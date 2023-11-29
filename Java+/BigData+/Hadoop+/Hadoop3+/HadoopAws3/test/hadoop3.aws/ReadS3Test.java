package hadoop3.aws;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

class ReadS3Test {
    @Test
    void listObjectsInBucket() throws URISyntaxException, IOException {
        var properties = FileUtil.homeDirFileToProperties(".aws-examples", "hadoop-aws.properties");
        var bucket = properties.getProperty("bucket_name");
        var keyId = properties.getProperty("aws_access_key_id");
        var secret = properties.getProperty("aws_secret_access_key");
        var conf = new Configuration();
        conf.set("fs.s3a.access.key", keyId);
        conf.set("fs.s3a.secret.key", secret);
        var s3Uri = new URI("s3a://" + bucket);
        var fs = FileSystem.get(s3Uri, conf);
        var statuses = fs.listStatus(new Path(s3Uri));
        for (var status : statuses) {
            System.out.println(status.getPath());
        }
        fs.close();
    }
}
