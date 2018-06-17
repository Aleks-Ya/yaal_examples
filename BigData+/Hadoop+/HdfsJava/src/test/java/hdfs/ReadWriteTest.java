package hdfs;

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

public class ReadWriteTest {
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
        DistributedFileSystem fileSystem = hdfsCluster.getFileSystem();

        Path p = new Path(hdfsURI, "tmp/my.txt");

        FSDataOutputStream myOS = fileSystem.create(p);
        String expContent = "hi!";
        myOS.writeUTF(expContent);
        myOS.close();

        FSDataInputStream myIS = fileSystem.open(p);
        String actContent = myIS.readUTF();
        myIS.close();

        assertEquals(expContent, actContent);

        hdfsCluster.shutdown();
        FileUtil.fullyDelete(baseDir);
    }
}
