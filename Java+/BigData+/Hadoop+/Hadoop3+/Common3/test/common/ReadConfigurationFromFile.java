package common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ReadConfigurationFromFile {
    private static final String DEFAULT_FS_KEY = "fs.defaultFS";
    private static final String DEFAULT_FS_VALUE = "hdfs://sandbox-hdp.hortonworks.com:8020";
    private final Configuration configuration = new Configuration(false);

    @Test
    void fromInputStream() {
        var is = ResourceUtil.resourceToInputStream(getClass(), "core-site.xml");
        configuration.addResource(is);
        configuration.reloadConfiguration();
        assertThat(configuration.size(), equalTo(40));
        assertThat(configuration.get(DEFAULT_FS_KEY), equalTo(DEFAULT_FS_VALUE));
    }

    @Test
    void fromResource() {
        configuration.addResource("common/core-site.xml");
        configuration.reloadConfiguration();
        assertThat(configuration.size(), equalTo(40));
        assertThat(configuration.get(DEFAULT_FS_KEY), equalTo(DEFAULT_FS_VALUE));
    }

    @Test
    void fromPath() {
        var pathString = ResourceUtil.resourceToPath(getClass(), "core-site.xml");
        var path = new Path(pathString);
        configuration.addResource(path);
        configuration.reloadConfiguration();
        assertThat(configuration.size(), equalTo(40));
        assertThat(configuration.get(DEFAULT_FS_KEY), equalTo(DEFAULT_FS_VALUE));
    }

    @Test
    void fromTwoResources() {
        var is1 = ResourceUtil.resourceToInputStream(getClass(), "core-site.xml");
        var is2 = ResourceUtil.resourceToInputStream(getClass(), "hdfs-site.xml");
        configuration.addResource(is1);
        configuration.addResource(is2);
        configuration.reloadConfiguration();
        assertThat(configuration.size(), equalTo(99));
        assertThat(configuration.get(DEFAULT_FS_KEY), equalTo(DEFAULT_FS_VALUE));
        assertThat(configuration.get("dfs.datanode.address"), equalTo("0.0.0.0:50010"));
        assertThat(configuration.get("fs.s3a.fast.upload"), equalTo("false"));//Override core-site.xml
    }
}