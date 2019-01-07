package hdfs.in_memory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class PlainTextReadWrite {
    @Test
    public void test() throws IOException {

        File baseDir = Files.createTempDirectory("test_hdfs_").toFile().getAbsoluteFile();
        System.out.println("Base Dir: " + baseDir);
        Configuration conf = new Configuration();
        conf.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, baseDir.getAbsolutePath());
        MiniDFSCluster.Builder builder = new MiniDFSCluster.Builder(conf);
        MiniDFSCluster hdfsCluster = builder.build();

        String hdfsURI = "hdfs://localhost:" + hdfsCluster.getNameNodePort() + "/";
        System.out.println("HDFS URI: " + hdfsURI);
        DistributedFileSystem fs = hdfsCluster.getFileSystem();

        Path path = new Path(hdfsURI, "tmp/my.txt");

        if (fs.exists(path)) {
            fs.delete(path, false);
        }

        FSDataOutputStream os = fs.create(path);
        String expContent = "hi!";
        os.writeUTF(expContent);
        os.close();

        FSDataInputStream is = fs.open(path);
        String actContent = is.readUTF();
        is.close();

        assertEquals(expContent, actContent);

        hdfsCluster.shutdown();
        FileUtil.fullyDelete(baseDir);
    }
}
