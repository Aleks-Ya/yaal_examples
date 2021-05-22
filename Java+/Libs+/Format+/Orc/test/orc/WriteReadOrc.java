package orc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.MapColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.log4j.BasicConfigurator;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class WriteReadOrc {
    private static final Logger log = LoggerFactory.getLogger(WriteReadOrc.class);

    @BeforeEach
    public void initLog4j() {
        BasicConfigurator.configure();
    }

    /**
     * https://orc.apache.org/docs/core-java.html#writing-orc-files
     */
    @Test
    public void simple() throws IOException {
        Path outPath = new Path(Files.createTempDirectory("orc").toString(), "simple.orc");
        log.info("Output file: {}", outPath);

        Configuration conf = new Configuration();
        writeSimple(conf, outPath);
        readSimple(conf, outPath);
    }

    private static void writeSimple(Configuration conf, Path outPath) throws IOException {
        TypeDescription schema = TypeDescription.fromString("struct<x:int,y:int>");
        Writer writer = OrcFile.createWriter(outPath, OrcFile.writerOptions(conf).setSchema(schema));

        VectorizedRowBatch batch = schema.createRowBatch();
        LongColumnVector x = (LongColumnVector) batch.cols[0];
        LongColumnVector y = (LongColumnVector) batch.cols[1];
        for (int r = 0; r < 100; ++r) {
            int row = batch.size++;
            x.vector[row] = r;
            y.vector[row] = r * 3;
            // If the batch is full, write it out and start over.
            if (batch.size == batch.getMaxSize()) {
                writer.addRowBatch(batch);
                batch.reset();
            }
        }
        if (batch.size != 0) {
            writer.addRowBatch(batch);
            batch.reset();
        }
        writer.close();
    }

    private static void readSimple(Configuration conf, Path outPath) throws IOException {
        Reader reader = OrcFile.createReader(outPath, OrcFile.readerOptions(conf));
        RecordReader rows = reader.rows();
        VectorizedRowBatch batchRead = reader.getSchema().createRowBatch();
        while (rows.nextBatch(batchRead)) {
            LongColumnVector colX = (LongColumnVector) batchRead.cols[0];
            LongColumnVector colY = (LongColumnVector) batchRead.cols[1];
            for (int r = 0; r < batchRead.size; ++r) {
                long x = colX.vector[r];
                long y = colY.vector[r];
                log.info("Row {}: {} - {}", r, x, y);
            }
        }
        rows.close();
    }

    /**
     * Source: https://orc.apache.org/docs/core-java.html#advanced-example
     */
    @Test
    public void advanced() throws IOException {
        java.nio.file.Path outDir = Files.createTempDirectory("orc");
        Path testFilePath = new Path(outDir.toString(), "advanced-example.orc");
        log.info("Output file: {}", testFilePath);
        Configuration conf = new Configuration();

        TypeDescription schema =
                TypeDescription.fromString("struct<first:int," +
                        "second:int,third:map<string,int>>");

        Writer writer =
                OrcFile.createWriter(testFilePath,
                        OrcFile.writerOptions(conf).setSchema(schema));

        VectorizedRowBatch batch = schema.createRowBatch();
        LongColumnVector first = (LongColumnVector) batch.cols[0];
        LongColumnVector second = (LongColumnVector) batch.cols[1];

        //Define map. You need also to cast the key and value vectors
        MapColumnVector map = (MapColumnVector) batch.cols[2];
        BytesColumnVector mapKey = (BytesColumnVector) map.keys;
        LongColumnVector mapValue = (LongColumnVector) map.values;

        // Each map has 5 elements
        final int MAP_SIZE = 5;
        final int BATCH_SIZE = batch.getMaxSize();

        // Ensure the map is big enough
        mapKey.ensureSize(BATCH_SIZE * MAP_SIZE, false);
        mapValue.ensureSize(BATCH_SIZE * MAP_SIZE, false);

        // add 1500 rows to file
        for (int r = 0; r < 1500; ++r) {
            int row = batch.size++;

            first.vector[row] = r;
            second.vector[row] = r * 3;

            map.offsets[row] = map.childCount;
            map.lengths[row] = MAP_SIZE;
            map.childCount += MAP_SIZE;

            for (int mapElem = (int) map.offsets[row];
                 mapElem < map.offsets[row] + MAP_SIZE; ++mapElem) {
                String key = "row " + r + "." + (mapElem - map.offsets[row]);
                mapKey.setVal(mapElem, key.getBytes(StandardCharsets.UTF_8));
                mapValue.vector[mapElem] = mapElem;
            }
            if (row == BATCH_SIZE - 1) {
                writer.addRowBatch(batch);
                batch.reset();
            }
        }
        if (batch.size != 0) {
            writer.addRowBatch(batch);
            batch.reset();
        }
        writer.close();
    }
}