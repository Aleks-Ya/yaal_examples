package linux;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

/**
 * Изменение владельца файла.
 */
public class Owner {

    @Test
    public void main() throws IOException {
        Path target = Files.createTempFile("target_", ".sh");
        target.toFile().deleteOnExit();

        String user = System.getenv("USERNAME");
        UserPrincipal owner = Files.getOwner(target);
        assertEquals(user, owner.getName());
        out.printf("target = %s%n", target);
        out.printf("%s%n", owner);


        UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
        String rootUser = "root";
        UserPrincipal newOwner = lookupService.lookupPrincipalByName(rootUser);

        //java.nio.file.FileSystemException: /tmp/target_989023771236240807.sh: Operation not permitted
        //Files.setOwner(target, newOwner);
        //assertEquals(rootUser, Files.getOwner(target).getName());
    }
}