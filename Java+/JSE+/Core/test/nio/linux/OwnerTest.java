package nio.linux;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.out;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Изменение владельца файла.
 */
class OwnerTest {

    @Test
    void setOwner() throws IOException {
        var target = Files.createTempFile("target_", ".sh");
        target.toFile().deleteOnExit();

        var exp = System.getenv("USERNAME");
        var owner = Files.getOwner(target);
        assertThat(owner.getName()).containsSubsequence(exp);
        out.printf("target = %s%n", target);
        out.printf("%s%n", owner);


//        UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
//        String rootUser = "root";
//        UserPrincipal newOwner = lookupService.lookupPrincipalByName(rootUser);

        //java.nio.file.FileSystemException: /tmp/target_989023771236240807.sh: Operation not permitted
        //Files.setOwner(target, newOwner);
        //assertEquals(rootUser, Files.getOwner(target).getName());
    }
}