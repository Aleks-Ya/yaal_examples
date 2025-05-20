package dataframe.action

import factory.Factory
import factory.Factory.ss
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ForEachPartitionActionTest extends AnyFlatSpec with Matchers {

  it should "perform action for each partition" in {
    val schema = StructType.fromDDL("name string, age int, gender string")
    val rows = Seq(Row("John", 25, "M"), Row("Peter", 35, "M"), Row("Mary", 20, "F"))
    val partitionNumber = 2
    val df = Factory.ss.createDataFrame(ss.sparkContext.parallelize(rows, partitionNumber), schema)
    val partitionsAcc = Factory.ss.sparkContext.collectionAccumulator[String]
    df.foreachPartition((rows: Iterator[Row]) => partitionsAcc.add(rows.toList.toString()))
    partitionsAcc.value should contain inOrderOnly(
      "List([John,25,M])",
      "List([Peter,35,M], [Mary,20,F])"
    )
  }

}
