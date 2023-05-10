package joplin.apps.img_tag_to_resource_link;

import joplin.common.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Replaces HTML <IMG> tag with MarkDown link to Joplin resource.
 * E.g. "<img src=":/fe3aac71c1e44393a4e54666e697dfc8" alt="picture.png" width="284" height="179" class="jop-noMdConv">"
 * -> "![picture.png](:/fe3aac71c1e44393a4e54666e697dfc8)"
 */
public class ImgTagToResourceLinkMain {
    private static final Logger log = LoggerFactory.getLogger(ImgTagToResourceLinkMain.class);

    public static void main(String[] args) {
        log.info("Started");
        try (var facade = Factory.createFacadeProd(false)) {
            var converter = new Converter(facade);
            converter.convert();
        }
    }
}
