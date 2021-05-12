package streaming.sql

//import org.apache.spark.sql.types.{StringType, StructField, StructType}
//import org.apache.spark.sql.{Row, SQLContext}
//import org.apache.spark.streaming.{Seconds, StreamingContext}
//import org.apache.spark.{SparkConf, SparkContext}
//import org.scalatest.BeforeAndAfter


class SqlTest
//  extends AnyFlatSpec with BeforeAndAfterAll
 {

//  "Visualize DF" should "print some info" in {
//    val conf = new SparkConf().setAppName(classOf[SqlTest].getSimpleName).setMaster("local[2]")
//    val sc = new SparkContext(conf)
//    val batchDuration = Seconds(5)
//    val ssc = new StreamingContext(sc, batchDuration)
//    val sql = new SQLContext(sc)
//
//    val lines = ssc.socketTextStream("localhost", 9999)
//    val schema = StructType(Seq(StructField("line_field", StringType, nullable = true)))
//    lines.foreachRDD { rdd => sql.createDataFrame(util.Arrays.asList(Row(rdd)), schema) }
//    ssc.start()
//    ssc.awaitTermination()
//  }
}
