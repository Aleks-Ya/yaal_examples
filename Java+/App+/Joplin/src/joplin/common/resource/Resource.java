package joplin.common.resource;

import java.io.File;

public record Resource(ResourceId resourceId, File resourceFile) {
}
