package hdfs.cluster.anonymus;

import cluster.FileUtil;
import cluster.HdfsFactory;
import cluster.ResourceUtil;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Connect to a real HDFS cluster.
 * Run Hadoop cluster (`Building+/Docker+/DockerImage+/Application+/Hadoop+/Hadoop2+/HadoopCluster`)
 */
class ConnectToRealHdfs {
    private final String hdfsUri = HdfsFactory.getHdfsUri();
    private final FileSystem fs = HdfsFactory.fileSystemAnonymous();

    @Test
    void writeUTF() throws IOException {
        var remotePath = new Path(hdfsUri, "/tmp/iablokov/my.txt");

        if (fs.exists(remotePath)) {
            fs.delete(remotePath, false);
        }

        var os = fs.create(remotePath);
        var expContent = "hi!";
        os.writeUTF(expContent);
        os.close();

        var is = fs.open(remotePath);
        var actContent = is.readUTF();
        is.close();

        assertThat(actContent).isEqualTo(expContent);
    }

    @Test
    void copyFromLocalFile() throws IOException {
        var expLocalFile = ResourceUtil.resourceToFile(getClass(), "copyFromLocalFile.txt");
        assertThat(expLocalFile).exists();
        var localPath = new Path(expLocalFile.toURI());
        var remotePath = new Path(hdfsUri, "/tmp/copyFromLocalFile.txt");

        if (fs.exists(remotePath)) {
            fs.delete(remotePath, false);
        }
        assertThat(fs.exists(remotePath)).isFalse();
        fs.copyFromLocalFile(localPath, remotePath);
        assertThat(fs.exists(remotePath)).isTrue();
        var actLocalFile = FileUtil.createAbsentTempFile(".txt");
        fs.copyToLocalFile(remotePath, new Path(actLocalFile.toURI()));
        assertThat(actLocalFile).hasSameBinaryContentAs(expLocalFile);
    }
}
