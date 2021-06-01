package hdfs.in_memory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainTextReadWrite {
    @Test
    void test() throws IOException {
        var baseDir = Files.createTempDirectory("test_hdfs_").toFile().getAbsoluteFile();
        System.out.println("Base Dir: " + baseDir);
        var conf = new Configuration();
        conf.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, baseDir.getAbsolutePath());
        var builder = new MiniDFSCluster.Builder(conf);
        var hdfsCluster = builder.build();

        var hdfsURI = "hdfs://localhost:" + hdfsCluster.getNameNodePort() + "/";
        System.out.println("HDFS URI: " + hdfsURI);
        var fs = hdfsCluster.getFileSystem();

        var path = new Path(hdfsURI, "tmp/my.txt");

        if (fs.exists(path)) {
            fs.delete(path, false);
        }

        var os = fs.create(path);
        var expContent = "hi!";
        os.writeUTF(expContent);
        os.close();

        var is = fs.open(path);
        var actContent = is.readUTF();
        is.close();

        assertEquals(expContent, actContent);

        hdfsCluster.shutdown();
        FileUtil.fullyDelete(baseDir);
    }
}
