package artist

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.spark.SparkContext

class DiscogsConf(val sc: SparkContext,
                  val fs: FileSystem,
                  val hadoopConf: Configuration,
                  val gzFile: String,
                  val seqFile: String)
