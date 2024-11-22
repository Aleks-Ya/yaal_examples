# Databricks Scala app

Submit a Scala app on a Standalone Spark cluster.

1. Build
    1. Choose Java 11: `sdk use java 11.0.25-zulu`
    2. Choose Scala 2.12: `sdk use scala 2.12.20`
    3. Build JAR: `./build_jar.sh`
2. Run locally
    1. Run a Spark cluster `BigData+/Spark+/SparkDocker+/Spark3StandaloneDocker`
    2. Submit: `./submit_cluster_mode.sh databricks.cluster.shorttrem.ShortTermDatabricksClusterModeApp`
3. Run in Databricks
    1. By Databricks UI
        1. Short-term job
            1. Type: `JAR`
            2. Main class: `databricks.cluster.shorttrem.ShortTermDatabricksClusterModeApp`
            3. JAR: `target/scala-2.12/databricksscalaspark3_2.12-1.jar`
            4. Parameters: `["Hello", "World"]`
        2. Long-term job
            1. Type: `JAR`
            2. Main class: `databricks.cluster.longterm.LongTermDatabricksClusterModeApp`
            3. JAR: `target/scala-2.12/databricksscalaspark3_2.12-1.jar`
            4. Parameters: `["60","30","120","180","35","45"]`
    2. By Databricks CLI
        1. Create an Instance Pool (if absents): `databricks instance-pools create --json @databricks/instance_pool.json`
        2. Upload JAR: `./upload_jar_to_dbfs.sh`
        3. Create job: `./create_job.sh databricks/cluster/shorttrem/ShortTermDatabricksClusterModeApp.json`
