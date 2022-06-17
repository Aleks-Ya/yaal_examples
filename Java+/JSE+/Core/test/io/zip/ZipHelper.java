package io.zip;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipHelper {
    public static void assertZipEntryEquals(ZipEntry exp, ZipEntry act) {
        assertThat(exp).isNotNull();
        assertThat(act).isNotNull();
        assertThat(act.getName()).isEqualTo(exp.getName());
        assertThat(act.getSize()).isEqualTo(exp.getSize());
    }

    public static void assertZipEntry(ZipFile zf, String entryName, String expEntryContent, long size) throws IOException {
        var aEntry = zf.getEntry(entryName);
        assertThat(aEntry.getSize()).isEqualTo(size);
        var ais = zf.getInputStream(aEntry);
        var aContent = IOUtils.toString(ais, Charset.defaultCharset());
        assertThat(aContent).isEqualTo(expEntryContent);
    }
}
