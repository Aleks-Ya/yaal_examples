package artist

import java.io._
import java.util
import java.util.zip.GZIPInputStream

import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.SequenceFile.Writer
import org.apache.hadoop.io._
import org.apache.hadoop.io.compress.DefaultCodec
import org.apache.hadoop.util.ReflectionUtils

object SplitXmlByArtist {

  def convertXmlGzToSequenceFile(conf: DiscogsConf): Unit = {
    val gzFile = conf.gzFile
    println("Write to sequence file: " + gzFile)
    val is: BufferedReader = null
    var writer: Writer = null
    try {
      val gzHadoopPath = new Path(gzFile)
      val is = new BufferedReader(
        new InputStreamReader(
          new GZIPInputStream(
            conf.fs.open(gzHadoopPath)
          )))

      writer = prepareOutputWriter(conf)

      var line: String = ""
      var nextLine: String = ""
      var lineCounter: Long = 0
      val printLineCounterEvery: Int = 10000
      while ( {
        nextLine = is.readLine
        nextLine != null
      }) {
        lineCounter += 1
        if (lineCounter % printLineCounterEvery == 0) println(s"Processed lines: $lineCounter. Artist counter: $artistCounter")
        line = line + nextLine
        val beginIndex = line.indexOf("<artist>")
        val endIndex = line.indexOf("</artist>")
        val endIndexFull = endIndex + "</artist>".length
        if (beginIndex >= 0 && endIndex > 0) {
          val artist = line.substring(beginIndex, endIndexFull)
          saveArtist(writer, artist)
          line = line.substring(endIndexFull)
        }
      }
    } finally {
      if (is != null) {
        is.close()
      }
      if (writer != null) {
        IOUtils.closeStream(writer)
      }
    }
  }

  val seqKey = new IntWritable
  val seqValue = new Text

  def prepareOutputWriter(conf: DiscogsConf): Writer = {
    val fs = conf.fs
    val path = new Path(conf.seqFile)
    var writer: Writer = null
    writer = SequenceFile.createWriter(
      conf.hadoopConf,
      Writer.file(path),
      Writer.keyClass(seqKey.getClass),
      Writer.valueClass(seqValue.getClass),
      Writer.bufferSize(fs.getConf.getInt("io.file.buffer.size", 4096)),
      Writer.replication(fs.getDefaultReplication(path)),
      Writer.blockSize(1073741824),
      Writer.compression(SequenceFile.CompressionType.BLOCK, new DefaultCodec),
      Writer.progressable(null),
      Writer.metadata(new SequenceFile.Metadata))
    writer
  }

  var artistCounter = 0

  private def saveArtist(writer: Writer, artist: String): Unit = {
    seqKey.set(artistCounter)
    seqValue.set(artist)
    writer.append(seqKey, seqValue)
    artistCounter += 1
  }

  def readToMap(conf: DiscogsConf): util.Map[Integer, String] = {
    println("Reading sequence file to map....")
    val hadoopConf = conf.hadoopConf
    val path: Path = new Path(conf.seqFile)
    var reader: SequenceFile.Reader = null
    val content: util.Map[Integer, String] = new util.HashMap[Integer, String]
    try {
      reader = new SequenceFile.Reader(hadoopConf,
        SequenceFile.Reader.file(path),
        SequenceFile.Reader.bufferSize(4096),
        SequenceFile.Reader.start(0))
      val key: IntWritable = ReflectionUtils.newInstance(reader.getKeyClass, hadoopConf).asInstanceOf[IntWritable]
      val value: Text = ReflectionUtils.newInstance(reader.getValueClass, hadoopConf).asInstanceOf[Text]
      while ( {
        reader.next(key, value)
      }) {
        val keyData: Integer = key.get
        val valueData: String = value.toString
        content.put(keyData, valueData)
      }
    } finally IOUtils.closeStream(reader)
    content
  }

}
