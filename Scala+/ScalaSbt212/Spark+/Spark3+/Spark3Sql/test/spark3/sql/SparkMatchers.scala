package spark3.sql

import org.apache.spark.sql.{DataFrame, Dataset}
import org.scalatest.matchers.should.Matchers

trait SparkMatchers extends Matchers {

  implicit class DataFrameShouldWrapper(df: DataFrame) {
    def shouldHaveDDL(expectedDdl: String): Unit = {
      df.schema.toDDL shouldEqual expectedDdl
    }

    def shouldContain(rows: String*): Unit = {
      df.toJSON.collect should contain inOrderElementsOf rows
    }

    def shouldContain(otherDf: DataFrame): Unit = {
      df.toJSON.collect shouldEqual otherDf.toJSON.collect
    }
  }

  implicit class DatasetShouldWrapper[T](ds: Dataset[T]) {
    def shouldHaveDDL(expectedDdl: String): Unit = {
      ds.schema.toDDL shouldEqual expectedDdl
    }

    def shouldContain(rows: T*): Unit = {
      ds.collect should contain inOrderElementsOf rows
    }

    def shouldContain(otherDs: Dataset[_]): Unit = {
      ds.collect should contain inOrderElementsOf otherDs.collect
    }
  }

}

