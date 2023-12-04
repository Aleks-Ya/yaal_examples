package core.source

import java.io.File

import core.Factory
import org.apache.hadoop.io.{IntWritable, Text}
import org.scalatest.{FlatSpec, Matchers}

class SequenceFileTest extends FlatSpec with Matchers {

  it should "init RDD from a sequence file" in {
    val data = Factory.sc.parallelize(List(("key1", 1), ("Kay2", 2)))
    val sequenceFile = File.createTempFile(getClass.getSimpleName, "seq")
    sequenceFile.deleteOnExit()
    val path = sequenceFile.getAbsolutePath
    sequenceFile.delete()
    data.saveAsSequenceFile(path)

    val rdd = Factory.sc.sequenceFile(path, classOf[Text], classOf[IntWritable])
    val list = rdd
      .map { case (x, y) => (x.toString, y.get()) }
      .collect()
    list should contain inOrderOnly(("key1", 1), ("Kay2", 2))
  }
}
