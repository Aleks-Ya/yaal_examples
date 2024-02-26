# Databricks Scala app

Submit a Scala app on a Standalone Spark cluster.

1. Run a Spark cluster `BigData+/Spark+/SparkDocker+/Spark3StandaloneDocker`.
2. Build
    1. Choose Java 11: `sdk use java 11.0.22-zulu`
    2. Choose Scala 2.12: `sdk use scala 2.12.18`
    3. Build JAR: `./build_jar.sh`
3. Submit to a local cluster with spark-submit:
   `./submit_cluster_mode.sh databricks.cluster.shorttrem.ShortTermDatabricksClusterModeApp`
4. Run in Databricks
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
        2. Get Instance Pool ID: `export POOL_ID=$(databricks instance-pools list --output JSON | jq -r 'first(.[] | select(.instance_pool_name == "pool-1") | .instance_pool_id)')`
        3. Short-term job
            1. Upload JAR: 
               1. Create folder: `databricks fs mkdirs dbfs:/jars`
               2. Upload JAR: `databricks fs cp --overwrite target/scala-2.12/databricksscalaspark3_2.12-1.jar dbfs:/jars/databricksscalaspark3.jar`
            2. Create the job: 
               1. Create job definition: `cat databricks/short_term_job.json | envsubst > /tmp/short_term_job.json`
               2. Create job: `databricks jobs create --json @/tmp/short_term_job.json`
