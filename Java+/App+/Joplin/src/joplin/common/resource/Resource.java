package joplin.common.resource;

import java.io.File;

public record Resource(ResourceId resourceId, File resourceFile) {
    public String getExtension() {
        var fileName = resourceFile.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public long getSize() {
        return resourceFile.length();
    }
}
