package io.zip;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ZipHelper {
    public static void assertZipEntryEquals(ZipEntry exp, ZipEntry act) {
        assertNotNull(exp);
        assertNotNull(act);
        assertEquals(exp.getName(), act.getName());
        assertEquals(exp.getSize(), act.getSize());
    }

    public static void assertZipEntry(ZipFile zf, String entryName, String expEntryContent, long size) throws IOException {
        ZipEntry aEntry = zf.getEntry(entryName);
        assertThat(aEntry.getSize(), equalTo(size));
        InputStream ais = zf.getInputStream(aEntry);
        String aContent = IOUtils.toString(ais, Charset.defaultCharset());
        assertThat(aContent, equalTo(expEntryContent));
    }
}
