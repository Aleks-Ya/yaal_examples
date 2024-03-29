/**
 * Build: sbt package
 * Copy JAR: docker cp /home/aleks/pr/home/yaal_examples/BigData+/Spark+/Spark2+/Spark2SQL/target/scala-2.11/spark2sql_2.11-1.jar spark_master_1:/tmp/
 * Run: docker exec -it spark_master_1 spark-submit --class sql.HelloWorldTest --master spark://master:7077 /tmp/spark2sql_2.11-1.jar
 */
package sql

import org.apache.spark.{SparkConf, SparkContext}

object HelloWorldTest {

  def main(args: Array[String]): Unit = {
    println("Start")
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val words = Seq("Hello, ", "World", "!")
    val greeting = sc.parallelize(words).reduce(_ + _)
    assert("Hello, World!".equals(greeting))
    println("Greeting: " + greeting)
    println("Finish")
  }

}