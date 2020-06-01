package glue

import com.amazonaws.services.glue.util.{GlueArgParser, Job, JsonOptions}
import com.amazonaws.services.glue.{DynamicFrame, GlueContext}
import org.apache.spark.SparkContext
import org.apache.spark.sql.{Dataset, Row}

import scala.collection.JavaConverters._

object KafkaToS3GlueApp {
  def main(sysArgs: Array[String]) {
    val spark: SparkContext = new SparkContext()
    val glueContext: GlueContext = new GlueContext(spark)
    // @params: [JOB_NAME]
    val args = GlueArgParser.getResolvedOptions(sysArgs, Seq("JOB_NAME").toArray)
    Job.init(args("JOB_NAME"), glueContext, args.asJava)
    // @type: DataSource
    // @args: [database = "iablokov-hw2", table_name = "daaa-iablokov-glue-test-topic", additionalOptions =
    //    {"startingOffsets": "earliest", "inferSchema": "true"}, stream_type = kafka]
    // @return: datasource0
    // @inputs: []
    val datasource0 = glueContext.getCatalogSource(
      database = "iablokov-hw2", tableName = "daaa-iablokov-glue-test-topic",
      redshiftTmpDir = "", transformationContext = "datasource0",
      additionalOptions = JsonOptions("""{"startingOffsets": "earliest", "inferSchema": "true"}""")
    ).getDataFrame()
    // @type: DataSink
    // @args: [mapping = [("model", "string", "model", "string"), ("year", "int", "year", "int"), ("changed", "string",
    //    "changed", "string"), ("ingest_year", "ingest_year"), ("ingest_month", "ingest_month"),
    //    ("ingest_day", "ingest_day"), ("ingest_hour", "ingest_hour")], database = "iablokov-hw2",
    //    table_name = "hw2_s3_output", stream_batch_time = "100 seconds",
    //    stream_checkpoint_location = "s3://iablokov-kafka-test/glue_hw2_kafka_s3/out/checkpoint/", transformation_ctx = "datasink1"]
    // @return: datasink1
    // @inputs: [frame = datasource0]
    glueContext.forEachBatch(datasource0, (dataFrame: Dataset[Row], batchId: Long) => {
      if (dataFrame.count() > 0) {
        val dynamicFrame = DynamicFrame(glueContext.addIngestionTimeColumns(dataFrame, "hour"), glueContext)
        val applyMapping = dynamicFrame.applyMapping(mappings = Seq(("model", "string", "model", "string"),
          ("year", "int", "year", "int"), ("changed", "string", "changed", "string"), ("ingest_year", "ingest_year"),
          ("ingest_month", "ingest_month"), ("ingest_day", "ingest_day"), ("ingest_hour", "ingest_hour")),
          caseSensitive = false, transformationContext = "applyMapping")
        val options = JsonOptions(Map(
          "partitionKeys" -> Seq("ingest_year", "ingest_month", "ingest_day", "ingest_hour"),
          "enableUpdateCatalog" -> true))
        val datasink1 = glueContext.getCatalogSink(database = "iablokov-hw2", tableName = "hw2_s3_output",
          redshiftTmpDir = "", transformationContext = "datasink1", additionalOptions = options)
          .writeDynamicFrame(applyMapping)
      }
    }, JsonOptions("""{"windowSize" : "100 seconds", "checkpointLocation" : "s3://iablokov-kafka-test/glue_hw2_kafka_s3/out/checkpoint/"}"""))
    Job.commit()
  }
}