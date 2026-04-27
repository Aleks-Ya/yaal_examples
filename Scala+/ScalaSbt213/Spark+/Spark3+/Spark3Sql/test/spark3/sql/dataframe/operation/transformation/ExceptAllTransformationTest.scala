package spark3.sql.dataframe.operation.transformation

import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class ExceptAllTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "remove DataFrame rows from another DataFrame" in {
    val vehiclesDf: DataFrame = Factory.createDf("vehicle STRING",
      Row("Car"),
      Row("Boat"),
      Row("Airplane"),
      Row("Ship"))
    val waterVehiclesDf: DataFrame = Factory.createDf("vehicle STRING",
      Row("Ship"),
      Row("Boat")
    )
    val diffDf: DataFrame = vehiclesDf.exceptAll(waterVehiclesDf)
    diffDf shouldHaveDDL "vehicle STRING"
    diffDf shouldContain(
      """{"vehicle":"Airplane"}""",
      """{"vehicle":"Car"}""")
  }

}