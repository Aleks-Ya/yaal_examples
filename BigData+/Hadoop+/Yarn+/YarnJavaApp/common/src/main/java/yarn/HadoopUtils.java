package yarn;

import org.apache.hadoop.fs.FileContext;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.ApplicationConstants.Environment;
import org.apache.hadoop.yarn.api.records.LocalResource;
import org.apache.hadoop.yarn.api.records.LocalResourceType;
import org.apache.hadoop.yarn.api.records.LocalResourceVisibility;
import org.apache.hadoop.yarn.api.records.URL;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class HadoopUtils {
    static final String APPLICATION_MASTER_JAR_NAME = "am.jar";
    static final Path APPLICATION_MASTER_JAR_PATH = new Path("/apps/" + APPLICATION_MASTER_JAR_NAME);

    static void setUpEnv(Map<String, String> env, YarnConfiguration conf, List<String> additionalClasspath) {
        StringBuilder classPathEnv = new StringBuilder(Environment.CLASSPATH.$$())
                .append(ApplicationConstants.CLASS_PATH_SEPARATOR)
                .append("./*");
        for (String c : conf.getStrings(YarnConfiguration.YARN_APPLICATION_CLASSPATH,
                YarnConfiguration.DEFAULT_YARN_CROSS_PLATFORM_APPLICATION_CLASSPATH)) {
            classPathEnv.append(ApplicationConstants.CLASS_PATH_SEPARATOR);
            classPathEnv.append(c.trim());
        }
        for (String path : additionalClasspath) {
            classPathEnv
                    .append(ApplicationConstants.CLASS_PATH_SEPARATOR)
                    .append(path);
        }

        if (conf.getBoolean(YarnConfiguration.IS_MINI_YARN_CLUSTER, false)) {
            classPathEnv.append(':');
            classPathEnv.append(System.getProperty("java.class.path"));
        }
        env.put("CLASSPATH", classPathEnv.toString());
    }

    static LocalResource setUpLocalResource(Path resPath, FileSystem hdfs) throws IOException {
        Path qPath = FileContext.getFileContext().makeQualified(resPath);
        FileStatus status = hdfs.getFileStatus(qPath);
        return LocalResource.newInstance(
                URL.fromPath(qPath),
                LocalResourceType.FILE,
                LocalResourceVisibility.PUBLIC,
                status.getLen(),
                status.getModificationTime());
    }
}

