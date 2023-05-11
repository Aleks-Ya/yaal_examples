package joplin.common.resource;

import java.io.File;
import java.util.List;

public record Resource(ResourceId resourceId, File resourceFile) {
    public String getExtension() {
        var fileName = resourceFile.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    public boolean hasExtension(List<String> extensions) {
        return extensions.stream().anyMatch(extension -> extension.equalsIgnoreCase(getExtension()));
    }

    public long getSize() {
        return resourceFile.length();
    }
}
