set -e
databricks fs mkdirs dbfs:/jars
databricks fs cp --overwrite target/scala-2.12/databricksscalaspark3_2.12-1.jar dbfs:/jars/databricksscalaspark3.jar